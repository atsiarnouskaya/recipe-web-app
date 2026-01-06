package com.WebApp.recipe.Services.UserSevices;

import com.WebApp.recipe.DTOs.UserDTOs.UserRequest;
import com.WebApp.recipe.DTOs.UserDTOs.UserResponse;
import com.WebApp.recipe.Entities.UserEntities.EmailVerification;
import com.WebApp.recipe.Entities.UserEntities.Role;
import com.WebApp.recipe.Entities.UserEntities.User;
import com.WebApp.recipe.Events.EmailVerificationEvent;
import com.WebApp.recipe.Exceptions.UserAlreadyExistsException;
import com.WebApp.recipe.Preprocessing.Normalizer;
import com.WebApp.recipe.Repositories.UserRepositories.EmailVerificationRepository;
import com.WebApp.recipe.Repositories.UserRepositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final EmailVerificationRepository emailVerificationRepository;
    private final EmailVerificationService emailVerificationService;
    private final ApplicationEventPublisher eventPublisher;

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);

    @Autowired
    public UserServiceImpl(UserRepository userRepository, EmailVerificationRepository emailVerificationRepository, EmailVerificationService emailVerificationService, ApplicationEventPublisher eventPublisher) {
        this.userRepository = userRepository;
        this.emailVerificationRepository = emailVerificationRepository;
        this.emailVerificationService = emailVerificationService;
        this.eventPublisher = eventPublisher;
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
    @Transactional
    public UserResponse signUpUser(UserRequest user) throws UserAlreadyExistsException, IllegalArgumentException {
        user = Normalizer.normalizeUserRequest(user);

        Optional<User> userOptional = userRepository.findUserByUsername(user.getUsername());
        Optional<EmailVerification> emailVerificationOptional = emailVerificationRepository.findByEmail(user.getEmail());

        if (emailVerificationOptional.isPresent() && emailVerificationOptional.get().getIsVerified()) {
            throw new UserAlreadyExistsException("User with this email already exists and is verified");
        } else if (emailVerificationOptional.isPresent() && !emailVerificationOptional.get().getIsVerified()) {
            throw new UserAlreadyExistsException("User with this email already exists and is not verified");
        }

        User newUser;

        if (userOptional.isPresent()) {
            EmailVerification ev;
            newUser = userOptional.get();
            ev = newUser.getEmailVerification();
            if (ev != null && ev.getIsVerified()) {
                throw new UserAlreadyExistsException("User with username: " + newUser.getUsername() + " already exists and is verified");
            }
            if (ev != null) {
                EmailVerificationEvent event = emailVerificationService.generateVerificationCode(ev);
                eventPublisher.publishEvent(event);
                throw new UserAlreadyExistsException("User with username: " + newUser.getUsername() + " already exists and is not verified. Verification code has been sent");
            }
        }

        EmailVerification emailVerification = new EmailVerification();

        newUser = new User(false, user.getUsername(), bCryptPasswordEncoder.encode(user.getPassword()));

        newUser.setRoles(List.of(new Role("ROLE_USER")));

        emailVerification.setEmail(user.getEmail());
        EmailVerificationEvent event = emailVerificationService.generateVerificationCode(emailVerification);
        newUser.setEmailVerification(emailVerification);
        emailVerification.setUser(newUser);

        newUser = userRepository.save(newUser);
        eventPublisher.publishEvent(event);

        return new UserResponse(newUser.getId(), newUser.getUsername(), newUser.getEmailVerification().getEmail());
    }
}
