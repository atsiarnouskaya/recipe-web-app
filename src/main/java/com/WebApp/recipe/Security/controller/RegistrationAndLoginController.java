package com.WebApp.recipe.Security.controller;

import com.WebApp.recipe.Security.DTOs.UserRequest;
import com.WebApp.recipe.Security.DTOs.UserResponse;
import com.WebApp.recipe.Security.ValidationInfo;
import com.WebApp.recipe.Security.exception.UserAlreadyExistsException;
import com.WebApp.recipe.Security.service.AuthService;
import com.WebApp.recipe.Security.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.web.bind.annotation.*;


import java.util.Optional;


@RestController
public class RegistrationAndLoginController {

    private final UserService userService;
    private final AuthService authService;

    @Autowired
    public RegistrationAndLoginController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }


    @PostMapping("/signup")
    public ResponseEntity<UserResponse> registerUser(@RequestBody UserRequest userRequest) throws UserAlreadyExistsException {
        UserResponse userResponse;
        try {
            userResponse = userService.signUpUser(userRequest);
        }
        catch (UserAlreadyExistsException | IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new UserResponse(0, null, e.getMessage()));
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }

    @PostMapping("/signin")
    public ResponseEntity<UserResponse> loginUser(@RequestBody UserRequest userRequest,
                                                  HttpServletRequest request,
                                                  HttpServletResponse response) throws UsernameNotFoundException {

        ValidationInfo validationInfo = userService.validateUserInfo(userRequest.getUsername(), userRequest.getPassword());
        System.out.println(validationInfo);
        if (!validationInfo.status()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        ResponseEntity<Boolean> isAuthenticated = authService.authenticate(userRequest.getUsername(), userRequest.getPassword(), request, response);

        if (Boolean.TRUE.equals(isAuthenticated.getBody())) {
            UserResponse user = userService.getUserByUsername(userRequest.getUsername());
            return ResponseEntity.ok(user);
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/logoutSuccess")
    public String logoutSuccess() {
        return "logoutSuccess";
    }


    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpSession session) {
        System.out.println("Logging out");
        if (session != null) {
            session.invalidate();
        }

        return ResponseEntity.noContent().build();
    }

}
