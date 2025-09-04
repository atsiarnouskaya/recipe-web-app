package com.WebApp.recipe.Security.service;

import com.WebApp.recipe.Security.entity.User;
import com.WebApp.recipe.Security.exception.UserAlreadyExistsException;

public interface UserService {

    User getUserByUsername(String username);

    User registerUser(User user) throws UserAlreadyExistsException;

}
