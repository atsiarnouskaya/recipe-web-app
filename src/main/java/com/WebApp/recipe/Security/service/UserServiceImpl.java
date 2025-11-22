package com.WebApp.recipe.Security.service;

import com.WebApp.recipe.Security.DTOs.UserRequest;
import com.WebApp.recipe.Security.DTOs.UserResponse;
import com.WebApp.recipe.Security.ValidationInfo;
import com.WebApp.recipe.Security.entity.Role;
import com.WebApp.recipe.Security.entity.User;
import com.WebApp.recipe.Security.exception.UserAlreadyExistsException;
import com.WebApp.recipe.Security.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserResponse getUserByUsername(String username) {
        Optional<User> user = userRepository.findUserByUsername(username);
        if (user.isPresent()) {
            return new UserResponse(user.get().getId(), user.get().getUsername());
        }
        throw new UsernameNotFoundException("User not found");
    }

    @Override
    public UserResponse signUpUser(UserRequest user) throws UserAlreadyExistsException, IllegalArgumentException {
        ValidationInfo validationInfo = validateUserInfo(user.getUsername(), user.getPassword());

        if (!validationInfo.status()) {
            throw new IllegalArgumentException(validationInfo.message());
        }

        Optional<User> userOptional = userRepository.findUserByUsername(user.getUsername().strip());
        User newUser;
        if (userOptional.isPresent()) {
            newUser = userOptional.get();
            throw new UserAlreadyExistsException("User with username: " + newUser.getUsername() + " already exists");
        } else {
            newUser = new User(true, user.getUsername().strip(), bCryptPasswordEncoder.encode(user.getPassword().strip()));
            System.out.println(user.getPassword().strip());
            newUser.setRoles(List.of(new Role("ROLE_USER")));
            newUser = userRepository.save(newUser);
            return new UserResponse(newUser.getId(), newUser.getUsername());
        }
    }

    @Override
    public ValidationInfo validateUserInfo(String username, String password) {
        username = username.strip();
        password = password.strip();
        String usernameRegex = "^[a-zA-Z0-9_-]+$";

        boolean usernameMatchesPattern = Pattern.matches(usernameRegex, username);
        int maxLength = 50;

        if (username.isEmpty() || password.isEmpty()) {
            return new ValidationInfo(false, "Username and password cannot be empty");
        }
        if (!usernameMatchesPattern) {
            return new ValidationInfo(false, "Only letters, numbers, '_' and '-' are allowed in username");
        }
        if (username.length() > maxLength || password.length() > maxLength) {
            return new ValidationInfo(false, "Username and password must not be longer than 50 characters");
        }
        return new ValidationInfo(true, "");
    }

}
