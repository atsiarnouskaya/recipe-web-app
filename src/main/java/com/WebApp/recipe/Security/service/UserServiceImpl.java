package com.WebApp.recipe.Security.service;

import com.WebApp.recipe.Security.entity.User;
import com.WebApp.recipe.Security.repository.UserRepository;

public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }
}
