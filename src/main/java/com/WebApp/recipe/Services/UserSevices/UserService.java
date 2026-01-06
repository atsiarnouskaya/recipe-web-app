package com.WebApp.recipe.Services.UserSevices;

import com.WebApp.recipe.DTOs.UserDTOs.UserRequest;
import com.WebApp.recipe.DTOs.UserDTOs.UserResponse;
import com.WebApp.recipe.Exceptions.UserAlreadyExistsException;

public interface UserService {

    UserResponse getUserByUsername(String username);

    UserResponse signUpUser(UserRequest user) throws UserAlreadyExistsException;

}
