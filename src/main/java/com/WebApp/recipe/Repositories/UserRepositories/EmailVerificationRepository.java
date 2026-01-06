package com.WebApp.recipe.Repositories.UserRepositories;

import com.WebApp.recipe.Entities.UserEntities.EmailVerification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface EmailVerificationRepository extends JpaRepository<EmailVerification, Long> {
    Optional<EmailVerification> findVerificationCodeByEmail(String email);
    Optional<EmailVerification> findByEmail(String email);
}
