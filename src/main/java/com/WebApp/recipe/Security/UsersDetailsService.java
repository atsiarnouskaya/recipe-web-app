package com.WebApp.recipe.Security;

import com.WebApp.recipe.Security.entity.User;
import com.WebApp.recipe.Security.entity.UserPrinciple;
import com.WebApp.recipe.Security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UsersDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    @Autowired
    public UsersDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<User> user = userRepository.findUserByUsername(username);


        if (user.isEmpty()) {
            throw new UsernameNotFoundException(username);
        }

        return new UserPrinciple(user.get());
    }
}
