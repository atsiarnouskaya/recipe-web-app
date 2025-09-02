package com.WebApp.recipe.Security.service;

import com.WebApp.recipe.Security.entity.User;

public interface UserService {

    User getUserByUsername(String username);

}
