package com.WebApp.recipe.Security.controller;

import com.WebApp.recipe.Security.DTOs.UserRequest;
import com.WebApp.recipe.Security.DTOs.UserResponse;
import com.WebApp.recipe.Security.exception.UserAlreadyExistsException;
import com.WebApp.recipe.Security.service.UserService;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.web.bind.annotation.*;


import java.util.Optional;


@RestController
public class RegistrationAndLoginController {

    private final UserService userService;

    @Autowired
    public RegistrationAndLoginController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/signup")
    public ResponseEntity<UserResponse> registerUser(@RequestBody UserRequest userRequest) throws UserAlreadyExistsException {
        UserResponse userResponse;
        try {
            userResponse = userService.signUpUser(userRequest);
        }
        catch (UserAlreadyExistsException e) {
            throw new UserAlreadyExistsException(e.getMessage());
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }

    @PostMapping("/signin")
    public ResponseEntity<UserResponse> signin(@RequestBody UserRequest userRequest,
                                               HttpSession session) throws UsernameNotFoundException {

        Optional<UserResponse> userResponseOptional = userService.validateUser(userRequest.getUsername(), userRequest.getPassword());

        if (userResponseOptional.isPresent()) {

            session.setAttribute("user", userResponseOptional.get().getUsername());
            session.setMaxInactiveInterval(30 * 60);
            return ResponseEntity.ok(userResponseOptional.get());
        }

        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);

    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpSession session) {

        if (session != null) {
            session.removeAttribute("user");
            session.invalidate();
        }

        return ResponseEntity.noContent().build();
    }

}
