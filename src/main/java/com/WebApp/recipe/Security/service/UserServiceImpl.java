package com.WebApp.recipe.Security.service;

import com.WebApp.recipe.Security.DTOs.UserRequest;
import com.WebApp.recipe.Security.DTOs.UserResponse;
import com.WebApp.recipe.Security.entity.EmailVerification;
import com.WebApp.recipe.Security.entity.Role;
import com.WebApp.recipe.Security.entity.User;
import com.WebApp.recipe.Security.exception.UserAlreadyExistsException;
import com.WebApp.recipe.Security.repository.EmailVerificationRepository;
import com.WebApp.recipe.Security.repository.UserRepository;
import com.WebApp.recipe.Utils.VerificationCodeService;
import com.WebApp.recipe.Security.DTOs.VerifyEmailDTO.VerificationRequest;
import com.WebApp.recipe.Security.DTOs.VerifyEmailDTO.VerificationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final EmailVerificationRepository emailVerificationRepository;
    private final EmailVerificationService emailVerificationService;

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);

    @Autowired
    public UserServiceImpl(UserRepository userRepository, EmailVerificationRepository emailVerificationRepository, EmailVerificationService emailVerificationService) {
        this.userRepository = userRepository;
        this.emailVerificationRepository = emailVerificationRepository;
        this.emailVerificationService = emailVerificationService;
    }

    @Override
    public UserResponse getUserByUsername(String username) {
        Optional<User> userOptional = userRepository.findUserByUsername(username);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            return new UserResponse(user.getId(), user.getUsername(), user.getEmailVerification().getEmail());
        }
        throw new UsernameNotFoundException("User not found");
    }

    @Override
    public UserResponse signUpUser(UserRequest user) throws UserAlreadyExistsException, IllegalArgumentException {

        Optional<User> userOptional = userRepository.findUserByUsername(user.getUsername().strip());
        Optional<EmailVerification> emailVerificationOptional = emailVerificationRepository.findByEmail(user.getEmail());

        if (emailVerificationOptional.isPresent()) {
            throw new UserAlreadyExistsException("User with this email already exists");
        }

        EmailVerification emailVerification = new EmailVerification();
        User newUser;
        if (userOptional.isPresent()) {
            newUser = userOptional.get();
            emailVerification = newUser.getEmailVerification();
            if (emailVerification != null && emailVerification.getIsVerified()) {
                throw new UserAlreadyExistsException("User with username: " + newUser.getUsername() + " already exists and is verified");
            }
            if (emailVerification != null) {
                emailVerificationService.generateAndSendVerificationEmail(emailVerification);
                throw new UserAlreadyExistsException("User with username: " + newUser.getUsername() + " already exists and is not verified. Verification code has been sent");
            }
        }

        newUser = new User(false, user.getUsername().strip(), bCryptPasswordEncoder.encode(user.getPassword().strip()));
        newUser.setRoles(List.of(new Role("ROLE_USER")));
        emailVerification.setEmail(user.getEmail());
        emailVerificationService.generateAndSendVerificationEmail(emailVerification);
        newUser.setEmailVerification(emailVerification);
        emailVerification.setUser(newUser);
        newUser = userRepository.save(newUser);
        return new UserResponse(newUser.getId(), newUser.getUsername(), newUser.getEmailVerification().getEmail());
    }
}
