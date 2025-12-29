package com.WebApp.recipe.Security.controller;

import com.WebApp.recipe.Security.service.EmailVerificationService;
import com.WebApp.recipe.Security.DTOs.VerifyEmailDTO.VerificationRequest;
import com.WebApp.recipe.Security.DTOs.VerifyEmailDTO.VerificationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class VerificationEmailController {

    private final EmailVerificationService emailVerificationService;

    @Autowired
    public VerificationEmailController(EmailVerificationService emailVerificationService) {
        this.emailVerificationService = emailVerificationService;
    }

    @PostMapping("/verify")
    public ResponseEntity<VerificationResponse> verify(@RequestBody VerificationRequest verificationRequest) {
        VerificationResponse verificationResponse = emailVerificationService.verifyCode(verificationRequest);

        if (verificationResponse.isVerified()) {
            return new ResponseEntity<>(verificationResponse, HttpStatus.OK);
        }
        return new ResponseEntity<>(verificationResponse, HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/resendVerificationCode")
    public ResponseEntity<VerificationResponse> resendVerificationCode(@RequestBody VerificationRequest verificationRequest) {
        VerificationResponse verificationResponse = emailVerificationService.resendVerificationCode(verificationRequest);
        return new ResponseEntity<>(verificationResponse, HttpStatus.OK);
    }
}
