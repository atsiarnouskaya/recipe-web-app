package com.WebApp.recipe.Controllers.UserControllers;

import com.WebApp.recipe.Services.UserSevices.EmailVerificationService;
import com.WebApp.recipe.DTOs.VerifyEmailDTO.VerificationRequest;
import com.WebApp.recipe.DTOs.VerifyEmailDTO.VerificationResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<VerificationResponse> verify(@RequestBody @Valid VerificationRequest verificationRequest) {
        VerificationResponse verificationResponse = emailVerificationService.verifyCode(verificationRequest);

        if (verificationResponse.isVerified()) {
            return new ResponseEntity<>(verificationResponse, HttpStatus.OK);
        }
        return new ResponseEntity<>(verificationResponse, HttpStatus.BAD_REQUEST);
    }

//    @PostMapping("/resendVerificationCode")
//    public ResponseEntity<VerificationResponse> resendVerificationCode(@RequestBody String email) {
//        VerificationResponse verificationResponse = emailVerificationService.resendVerificationCode(email);
//        return new ResponseEntity<>(verificationResponse, HttpStatus.OK);
//    }
}
