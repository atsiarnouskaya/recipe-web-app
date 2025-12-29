package com.WebApp.recipe.Security.service;

import com.WebApp.recipe.Security.DTOs.VerifyEmailDTO.VerificationRequest;
import com.WebApp.recipe.Security.DTOs.VerifyEmailDTO.VerificationResponse;
import com.WebApp.recipe.Security.entity.EmailVerification;
import com.WebApp.recipe.Security.entity.User;
import com.WebApp.recipe.Security.repository.EmailVerificationRepository;
import com.WebApp.recipe.Utils.VerificationCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.time.LocalDateTime;

@Service
public class EmailVerificationService {
    private static final int TOKEN_EXPIRE_MINUTES = 10;
    private static final int TOKEN_MAX_ATTEMPTS = 5;

    private final EmailVerificationRepository emailVerificationRepository;
    private final EmailService emailService;

    @Autowired
    public EmailVerificationService(EmailVerificationRepository emailVerificationRepository, EmailService emailService) {
        this.emailVerificationRepository = emailVerificationRepository;
        this.emailService = emailService;
    }
    
    public VerificationResponse verifyCode(VerificationRequest verificationRequest) {
        String code = verificationRequest.getVerificationCode();
        String email = verificationRequest.getEmail();
        String hashedToken = VerificationCodeService.hashCode(code);
        Optional<EmailVerification> emailVerificationOptional = emailVerificationRepository.findVerificationCodeByEmail(email);

        if (emailVerificationOptional.isPresent()) {
            EmailVerification emailVerification = emailVerificationOptional.get();
            User user = emailVerification.getUser();

            if (emailVerification.getIsVerified()) {
                user.setEnabled(true);
                return new VerificationResponse(true, TOKEN_MAX_ATTEMPTS, "The user is already verified.");
            }

            if (emailVerification.getVerificationCodeAttempts() >= TOKEN_MAX_ATTEMPTS) {
                user.setEnabled(false);
                return new VerificationResponse(false, 0, "The user tried to verify too many times.");
            }

            if (emailVerification.getVerificationCodeExpiresAt().isBefore(LocalDateTime.now())) {
                user.setEnabled(false);
                emailVerification.setIsVerified(false);
                emailVerification.setVerificationCodeAttempts(0);
                emailVerificationRepository.save(emailVerification);
                return new VerificationResponse(false, TOKEN_MAX_ATTEMPTS - emailVerification.getVerificationCodeAttempts(), "Code is expired. Please generate a new one.");
            }

            if (!hashedToken.equals(emailVerification.getVerificationCode())) {
                user.setEnabled(false);
                emailVerification.setIsVerified(false);
                emailVerification.setVerificationCodeAttempts(emailVerification.getVerificationCodeAttempts() + 1);
                emailVerificationRepository.save(emailVerification);
                return new VerificationResponse(false, TOKEN_MAX_ATTEMPTS - emailVerification.getVerificationCodeAttempts(), "Code is incorrect. Please try again.");
            }

            user.setEnabled(true);
            emailVerification.setIsVerified(true);
            emailVerification.setVerificationCode(null);
            emailVerification.setVerificationCodeCreatedAt(null);
            emailVerification.setVerificationCodeExpiresAt(null);
            emailVerification.setVerificationCodeAttempts(0);
            emailVerificationRepository.save(emailVerification);
            return new VerificationResponse(true, TOKEN_MAX_ATTEMPTS - emailVerification.getVerificationCodeAttempts(), "The email is verified.");
        }

        return new VerificationResponse(false, 0, "There is no user registered with this email.");
    }

    public VerificationResponse resendVerificationCode(VerificationRequest emailVerificationRequest) {
        Optional<EmailVerification> emailVerificationOptional = emailVerificationRepository.findVerificationCodeByEmail(emailVerificationRequest.getEmail());
        if (emailVerificationOptional.isPresent()) {
            EmailVerification emailVerification = emailVerificationOptional.get();
            User user = emailVerification.getUser();
            user.setEnabled(false);
            generateAndSendVerificationEmail(emailVerification);
            return new VerificationResponse(false, TOKEN_MAX_ATTEMPTS, "The code has been resent");
        }
        return new VerificationResponse(false, 0, "There is no user registered with this email.");
    }

    public void generateAndSendVerificationEmail(EmailVerification emailVerification) {
        String email = emailVerification.getEmail();

        String code = VerificationCodeService.generate6DigitCode();
        String hashedCode = VerificationCodeService.hashCode(code);

        emailVerification.setVerificationCode(hashedCode);
        emailVerification.setVerificationCodeCreatedAt(LocalDateTime.now());
        emailVerification.setVerificationCodeExpiresAt(LocalDateTime.now().plusMinutes(TOKEN_EXPIRE_MINUTES));
        emailVerification.setVerificationCodeAttempts(0);
        emailVerification.setIsVerified(false);

        emailService.sendVerificationEmail(email, code);

        emailVerificationRepository.save(emailVerification);
    }

}

