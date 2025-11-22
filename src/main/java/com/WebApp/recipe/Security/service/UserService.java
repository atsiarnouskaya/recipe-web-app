package com.WebApp.recipe.Security.service;

import com.WebApp.recipe.Security.DTOs.UserRequest;
import com.WebApp.recipe.Security.DTOs.UserResponse;
import com.WebApp.recipe.Security.ValidationInfo;
import com.WebApp.recipe.Security.entity.User;
import com.WebApp.recipe.Security.exception.UserAlreadyExistsException;

import java.util.Optional;

public interface UserService {

    UserResponse getUserByUsername(String username);

    UserResponse signUpUser(UserRequest user) throws UserAlreadyExistsException;

    ValidationInfo validateUserInfo(String username, String password);

}
