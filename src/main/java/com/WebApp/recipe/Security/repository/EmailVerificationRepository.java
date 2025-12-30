package com.WebApp.recipe.Security.repository;

import com.WebApp.recipe.Security.entity.EmailVerification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmailVerificationRepository extends JpaRepository<EmailVerification, Long> {
    Optional<EmailVerification> findVerificationCodeByEmail(String email);
    Optional<EmailVerification> findByEmail(String email);
}
