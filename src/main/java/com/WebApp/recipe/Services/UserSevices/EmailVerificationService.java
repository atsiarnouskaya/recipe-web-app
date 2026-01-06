package com.WebApp.recipe.Services.UserSevices;

import com.WebApp.recipe.DTOs.VerifyEmailDTO.VerificationRequest;
import com.WebApp.recipe.DTOs.VerifyEmailDTO.VerificationResponse;
import com.WebApp.recipe.Entities.UserEntities.EmailVerification;
import com.WebApp.recipe.Entities.UserEntities.User;
import com.WebApp.recipe.Events.EmailVerificationEvent;
import com.WebApp.recipe.Preprocessing.Normalizer;
import com.WebApp.recipe.Repositories.UserRepositories.EmailVerificationRepository;
import com.WebApp.recipe.Utils.VerificationCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.time.LocalDateTime;

@Service
public class EmailVerificationService {
    private static final int TOKEN_EXPIRE_MINUTES = 10;
    private static final int TOKEN_MAX_ATTEMPTS = 5;

    private final EmailVerificationRepository emailVerificationRepository;

    @Autowired
    public EmailVerificationService(EmailVerificationRepository emailVerificationRepository) {
        this.emailVerificationRepository = emailVerificationRepository;
    }

    @Transactional
    public VerificationResponse verifyCode(VerificationRequest verificationRequest) {
        VerificationRequest normalizedVerificationRequest = Normalizer.normalizeVerificationRequest(verificationRequest);

        String code = normalizedVerificationRequest.getVerificationCode();
        String email = normalizedVerificationRequest.getEmail();
        String hashedToken = VerificationCodeService.hashCode(code);

        Optional<EmailVerification> emailVerificationOptional = emailVerificationRepository.findVerificationCodeByEmail(email);

        if (emailVerificationOptional.isPresent()) {
            EmailVerification emailVerification = emailVerificationOptional.get();
            User user = emailVerification.getUser();

            if(emailVerification.getVerificationCode() == null || emailVerification.getVerificationCodeExpiresAt() == null) {
                return new VerificationResponse(false, email, TOKEN_MAX_ATTEMPTS, "The user does not have a verification code");
            }

            if (emailVerification.getIsVerified()) {
                user.setEnabled(true);
                return new VerificationResponse(true, email, TOKEN_MAX_ATTEMPTS, "The user is already verified.");
            }

            if (emailVerification.getVerificationCodeAttempts() >= TOKEN_MAX_ATTEMPTS) {
                user.setEnabled(false);

                emailVerification.setIsVerified(false);
                emailVerification.setVerificationCode(null);
                emailVerification.setVerificationCodeExpiresAt(null);
                emailVerification.setVerificationCodeAttempts(0);

                emailVerificationRepository.save(emailVerification);

                return new VerificationResponse(false, email, 0, "You tried to verify your email too many times. Please generate a new code.");
            }

            if (emailVerification.getVerificationCodeExpiresAt().isBefore(LocalDateTime.now())) {
                user.setEnabled(false);

                emailVerification.setIsVerified(false);
                emailVerification.setVerificationCode(null);
                emailVerification.setVerificationCodeExpiresAt(null);
                emailVerification.setVerificationCodeAttempts(0);

                emailVerificationRepository.save(emailVerification);

                return new VerificationResponse(false, email, TOKEN_MAX_ATTEMPTS, "Code is expired. Please generate a new one.");
            }

            if (!hashedToken.equals(emailVerification.getVerificationCode())) {
                user.setEnabled(false);

                emailVerification.setIsVerified(false);
                emailVerification.setVerificationCodeAttempts(emailVerification.getVerificationCodeAttempts() + 1);
                emailVerificationRepository.save(emailVerification);

                int attemptsLeft = TOKEN_MAX_ATTEMPTS - emailVerification.getVerificationCodeAttempts();

                return new VerificationResponse(false, email, attemptsLeft, "Code is incorrect. Please try again.");
            }

            user.setEnabled(true);
            emailVerification.setIsVerified(true);
            emailVerification.setVerificationCode(null);
            emailVerification.setVerificationCodeExpiresAt(null);
            emailVerification.setVerificationCodeAttempts(0);

            emailVerificationRepository.save(emailVerification);

            return new VerificationResponse(true, email, TOKEN_MAX_ATTEMPTS, "The email is verified.");
        }

        return new VerificationResponse(false, email,0, "There is no user registered with this email.");
    }

//    public VerificationResponse resendVerificationCode(String email) {
//        Optional<EmailVerification> emailVerificationOptional = emailVerificationRepository.findVerificationCodeByEmail(email);
//        if (emailVerificationOptional.isPresent()) {
//            EmailVerification emailVerification = emailVerificationOptional.get();
//            User user = emailVerification.getUser();
//            user.setEnabled(false);
//            generateAndSendVerificationEmail(emailVerification);
//            return new VerificationResponse(false, email, TOKEN_MAX_ATTEMPTS, "The code has been resent");
//        }
//        return new VerificationResponse(false, email,0, "There is no user registered with this email.");
//    }

    public EmailVerificationEvent generateVerificationCode(EmailVerification emailVerification) {
        String email = emailVerification.getEmail();

        String code = VerificationCodeService.generate6DigitCode();
        String hashedCode = VerificationCodeService.hashCode(code);

        emailVerification.setVerificationCode(hashedCode);
        emailVerification.setVerificationCodeExpiresAt(LocalDateTime.now().plusMinutes(TOKEN_EXPIRE_MINUTES));
        emailVerification.setVerificationCodeAttempts(0);
        emailVerification.setIsVerified(false);

        emailVerificationRepository.save(emailVerification);

        return new EmailVerificationEvent(email, code);
    }

}

