package com.WebApp.recipe.Security.DTOs.VerifyEmailDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class VerificationResponse {
    private boolean isVerified;
    private int attemptsLeft;
    private String message;
}