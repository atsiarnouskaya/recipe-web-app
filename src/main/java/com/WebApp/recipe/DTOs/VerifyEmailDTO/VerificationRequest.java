package com.WebApp.recipe.DTOs.VerifyEmailDTO;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class VerificationRequest {

    @NotBlank(message = "Email cannot be empty")
    @Size(min = 1, max = 55, message = "length must be from 1 to 55 symbols")
    @Pattern(regexp = "^[a-z0-9]+@[a-z0-9]+\\.[a-z]{2,4}$", message = "Email address is invalid")
    private String email;

    @NotBlank(message = "Code cannot be empty")
    @Size(min = 1, max = 6, message = "length must be from 1 to 6 symbols")
    @Pattern(regexp = "[0-9]{6}", message = "Verification code is invalid")
    private String verificationCode;
}