package com.WebApp.recipe.Security.DTOs.VerifyEmailDTO;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class VerificationRequest {
    private String email;
    private String verificationCode;
}