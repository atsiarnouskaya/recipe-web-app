package com.WebApp.recipe.Preprocessing;

import com.WebApp.recipe.DTOs.UserDTOs.UserRequest;
import com.WebApp.recipe.DTOs.VerifyEmailDTO.VerificationRequest;

public class Normalizer {
    static public UserRequest normalizeUserRequest(UserRequest userRequest) {
        String username = userRequest.getUsername()
                .strip()
                .replaceAll("\\s+", " ");

        String email = userRequest.getEmail()
                .strip()
                .replaceAll("\\s+", "")
                .toLowerCase();

        return new UserRequest(username, userRequest.getPassword(), email);
    }

    static public VerificationRequest normalizeVerificationRequest(VerificationRequest verificationRequest) {
        String email = verificationRequest.getEmail()
                .strip()
                .replaceAll("\\s+", "")
                .toLowerCase();
        return new VerificationRequest(email, verificationRequest.getVerificationCode());
    }
}
