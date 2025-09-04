package com.WebApp.recipe.Security.controller;

import com.WebApp.recipe.Security.DTOs.UserRequest;
import com.WebApp.recipe.Security.DTOs.UserResponse;
import com.WebApp.recipe.Security.UsersDetailsService;
import com.WebApp.recipe.Security.entity.User;
import com.WebApp.recipe.Security.exception.UserAlreadyExistsException;
import com.WebApp.recipe.Security.service.UserService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RegistrationAndLoginController {

    private final UserService userService;

    public RegistrationAndLoginController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/register")
    public UserResponse registerUser(@RequestBody UserRequest userRequest) throws UserAlreadyExistsException {
        User user = new User(true, userRequest.getUsername(), userRequest.getPassword());

        try {
            userService.registerUser(user);
        }
        catch (UserAlreadyExistsException e) {
            throw new UserAlreadyExistsException(e.getMessage());
        }

        return new UserResponse(user.getUsername());
    }
}
