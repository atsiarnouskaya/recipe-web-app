package com.WebApp.recipe.Events;


import com.WebApp.recipe.Entities.UserEntities.EmailVerification;
import com.WebApp.recipe.Entities.UserEntities.User;
import com.WebApp.recipe.Repositories.UserRepositories.EmailVerificationRepository;
import com.WebApp.recipe.Repositories.UserRepositories.UserRepository;
import com.WebApp.recipe.Services.UserSevices.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.time.LocalDateTime;
import java.util.Optional;

@Component
public class EmailVerificationEventListener {
    private final EmailService emailService;
    private final EmailVerificationRepository emailVerificationRepository;

    @Autowired
    public EmailVerificationEventListener(EmailService emailService, EmailVerificationRepository emailVerificationRepository) {
        this.emailService = emailService;
        this.emailVerificationRepository = emailVerificationRepository;
    }

    @Async
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void sendVerificationEmail(EmailVerificationEvent event) {
        Optional<EmailVerification> emailVerificationOptional = emailVerificationRepository.findVerificationCodeByEmail(event.getEmail());

        if (emailVerificationOptional.isPresent()) {
            EmailVerification emailVerification = emailVerificationOptional.get();

            emailVerification.setVerificationCodeExpiresAt(LocalDateTime.now().plusMinutes(10));
            emailVerificationRepository.save(emailVerification);
            emailService.sendVerificationEmail(event.getEmail(), event.getToken());
        }
    }
}