package com.WebApp.recipe.Services.UserSevices;

import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@Async
public class EmailService {

    private final JavaMailSender mailSender;

    @Autowired
    public EmailService(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public void sendVerificationEmail(String email, String verificationToken) {
        String subject = "Please confirm your email";
        String message = "Verification code: ";
        sendEmail(email, verificationToken, subject, message);
    }


    public void sendForgotPasswordEmail(String email, String resetToken) {
        String subject = "Reset password";
        String message = "If you forgot your password, please provide this code to reset your password: ";
        sendEmail(email, resetToken, subject, message);
    }


    private void sendEmail(String email, String token, String subject, String message) {
        try {
            String content = """
                    <div style="font-family: Arial, sans-serif; max-width: 600px; margin: auto; padding: 20px; border-radius: 8px; background-color: #f9f9f9; text-align: center;">
                        <h2 style="color: #333;">%s</h2>
                        <p style="font-size: 16px; color: #555;">%s</p>
                        <p style="font-size: 14px; color: #007bff;">%s</p>
                        <p style="font-size: 12px; color: #aaa;">This message was generated automatically. Please do not reply</p>
                    </div>
                """.formatted(subject, message, token);

            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

            helper.setTo(email);
            helper.setSubject(subject);
            helper.setFrom(new InternetAddress(
                    "nastyaternovskaya01@gmail.com", "No Reply"
            ));
            helper.setText(content, true);
            mailSender.send(mimeMessage);

        } catch (Exception e) {
            System.err.println("Failed to send email: " + e.getMessage());
        }
    }
}