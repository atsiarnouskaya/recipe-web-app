package com.WebApp.recipe.DTOs.VerifyEmailDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class VerificationResponse {
    private boolean isVerified;
    private String email;
    private int attemptsLeft;
    private String message;
}