package com.WebApp.recipe.Security.service;

import com.WebApp.recipe.Security.DTOs.UserRequest;
import com.WebApp.recipe.Security.DTOs.UserResponse;
import com.WebApp.recipe.Security.exception.UserAlreadyExistsException;

public interface UserService {

    UserResponse getUserByUsername(String username);

    UserResponse signUpUser(UserRequest user) throws UserAlreadyExistsException;
}
