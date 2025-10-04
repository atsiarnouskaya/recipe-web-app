package com.WebApp.recipe.Security.controller;

import com.WebApp.recipe.Security.DTOs.UserRequest;
import com.WebApp.recipe.Security.DTOs.UserResponse;
import com.WebApp.recipe.Security.UsersDetailsService;
import com.WebApp.recipe.Security.service.UserService;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import org.springframework.web.bind.annotation.*;


import java.util.Optional;


@RestController
public class RegistrationAndLoginController {

    private final UserService userService;
    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);

    public RegistrationAndLoginController(UserService userService, UsersDetailsService usersDetailsService) {
        this.userService = userService;
    }


//    @PostMapping("/signup")
//    public UserResponse registerUser(@RequestBody UserRequest userRequest) throws UserAlreadyExistsException {
//        User user = new User(true, userRequest.getUsername(), userRequest.getPassword());
//
//        try {
//            user = userService.signUpUser(user);
//        }
//        catch (UserAlreadyExistsException e) {
//            throw new UserAlreadyExistsException(e.getMessage());
//        }
//
//        return new UserResponse(user.getId(), user.getUsername());
//    }

    @PostMapping("/signin")
    public UserResponse signin(@RequestBody UserRequest userRequest) throws UsernameNotFoundException {
        Optional<UserResponse> userResponseOptional = userService.validateUser(userRequest.getUsername(), userRequest.getPassword());
        if (userResponseOptional.isPresent()) {
            System.out.println(userResponseOptional.get().getUsername());
            return userResponseOptional.get();
        }
        throw new BadCredentialsException("Invalid username or password");

    }


//    @GetMapping("/me")
//    public UserResponse me(Principal userAuth) {
//        User user = userService.getUserByUsername(userAuth.getName());
//
//        return new UserResponse(user.getId(), user.getUsername());
//    }
}
