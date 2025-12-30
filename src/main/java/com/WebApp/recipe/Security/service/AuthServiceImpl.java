package com.WebApp.recipe.Security.service;

import com.WebApp.recipe.Security.entity.User;
import com.WebApp.recipe.Security.exception.UserAlreadyExistsException;
import com.WebApp.recipe.Security.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.context.SecurityContextHolderStrategy;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final SecurityContextRepository securityContextRepository;
    private final SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();

    public AuthServiceImpl(AuthenticationManager authenticationManager, UserRepository userRepository, SecurityContextRepository securityContextRepository) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.securityContextRepository = securityContextRepository;
    }


    @Override
    public ResponseEntity<Boolean> authenticate(String username, String password, HttpServletRequest request, HttpServletResponse response) throws UserAlreadyExistsException {
        ResponseEntity<Boolean> responseEntity = null;
        Optional<User> userOptional = userRepository.findUserByUsername(username);

        try {
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username, password);
            Authentication authentication = authenticationManager.authenticate(auth);
            if (authentication.isAuthenticated()) {
                if (userOptional.isPresent()) {
                    User user = userOptional.get();
                    if (!user.isEnabled()){
                        throw new UserAlreadyExistsException("User already exists, but email is not verified. Please verify your email to log in");
                    }
                }
                SecurityContext context = securityContextHolderStrategy.createEmptyContext();
                context.setAuthentication(authentication);
                securityContextHolderStrategy.setContext(context);
                securityContextRepository.saveContext(context, request, response);
                responseEntity = ResponseEntity.ok(true);
            }
        } catch (UserAlreadyExistsException e) {
            throw e;
        }
        catch (Exception e) {
            throw new BadCredentialsException(e.getMessage());
        }

        return responseEntity;
    }
}
