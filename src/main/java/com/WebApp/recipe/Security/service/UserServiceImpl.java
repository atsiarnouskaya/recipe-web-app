package com.WebApp.recipe.Security.service;

import com.WebApp.recipe.Security.entity.Role;
import com.WebApp.recipe.Security.entity.User;
import com.WebApp.recipe.Security.exception.UserAlreadyExistsException;
import com.WebApp.recipe.Security.repository.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findUserByUsername(username);
    }

    @Override
    public User registerUser(User user) throws UserAlreadyExistsException {
        User newUser = userRepository.findUserByUsername(user.getUsername());

        if (newUser == null) {
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            user.setRoles(List.of(new Role("ROLE_USER")));
            return userRepository.save(user);
        }
        else {
            throw new UserAlreadyExistsException("User with username: " + user.getUsername() + " already exists");
        }

    }
}
