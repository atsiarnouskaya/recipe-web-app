package com.WebApp.recipe.Security.service;

import com.WebApp.recipe.Security.DTOs.UserRequest;
import com.WebApp.recipe.Security.DTOs.UserResponse;
import com.WebApp.recipe.Security.entity.Role;
import com.WebApp.recipe.Security.entity.User;
import com.WebApp.recipe.Security.exception.UserAlreadyExistsException;
import com.WebApp.recipe.Security.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(12);

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserResponse getUserByUsername(String username) {
        Optional<User> user = userRepository.findUserByUsername(username);
        if (user.isPresent()) {
            return new UserResponse(user.get().getId(), user.get().getUsername());
        }
        throw new UsernameNotFoundException("User not found");
    }

    @Override
    public UserResponse signUpUser(UserRequest user) throws UserAlreadyExistsException, IllegalArgumentException {
        if (user.getPassword().isEmpty() || user.getUsername().isEmpty() || user.getPassword().isBlank() || user.getUsername().isBlank()) {
            throw new IllegalArgumentException("Username and password cannot be empty");
        }
        if (user.getUsername().length() > 50 || user.getPassword().length() > 50) {
            throw new IllegalArgumentException("Username and password must not be longer than 50 characters");
        }
        Optional<User> userOptional = userRepository.findUserByUsername(user.getUsername().strip());
        User newUser;
        if (userOptional.isPresent()) {
            newUser = userOptional.get();
            throw new UserAlreadyExistsException("User with username: " + newUser.getUsername() + " already exists");
        } else {
            newUser = new User(true, user.getUsername().strip(), bCryptPasswordEncoder.encode(user.getPassword().strip()));
            System.out.println(user.getPassword().strip());
            newUser.setRoles(List.of(new Role("ROLE_USER")));
            newUser = userRepository.save(newUser);
            return new UserResponse(newUser.getId(), newUser.getUsername());
        }
    }

    @Override
    public Optional<UserResponse> validateUser(String username, String password) {

        Optional<User> user = userRepository.findUserByUsername(username);

        if (user.isPresent()) {
            return bCryptPasswordEncoder.matches(password, user.get().getPassword())
                        ?
                    Optional.of(new UserResponse(user.get().getId(), user.get().getUsername()))
                        :
                    Optional.empty();
        }
        throw new UsernameNotFoundException("User not found");
    }
}
