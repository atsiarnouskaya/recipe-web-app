package com.WebApp.recipe.Utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

public class VerificationCodeService {
    private static final SecureRandom random = new SecureRandom();

    //this code I sent to a user
    public static String generate6DigitCode() {
        int code = random.nextInt(1_000_000); // 0..999999
        return String.format("%06d", code);
    }

    //the hash is saved into the db
    public static String hashCode(String code) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(code.getBytes());
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
}

