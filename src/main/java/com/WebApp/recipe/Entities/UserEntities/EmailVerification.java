package com.WebApp.recipe.Entities.UserEntities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.LocalDateTime;


@Getter
@Setter
@Entity
@Table(name = "email_verification")
public class EmailVerification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "user_id")
    private User user;

    @Size(max = 80)
    @Column(name = "email", length = 80)
    private String email;

    @Size(max = 80)
    @Column(name = "verification_code", length = 80)
    private String verificationCode;

    @Column(name = "verification_code_expires_at")
    private LocalDateTime verificationCodeExpiresAt;

    @Column(name = "verification_code_attempts")
    private Integer verificationCodeAttempts;

    @NotNull
    @ColumnDefault("0")
    @Column(name = "is_verified", nullable = false)
    private Boolean isVerified;

}