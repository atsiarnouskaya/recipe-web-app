package com.WebApp.recipe.Security.service;

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

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final SecurityContextRepository securityContextRepository;
    private final SecurityContextHolderStrategy securityContextHolderStrategy = SecurityContextHolder.getContextHolderStrategy();

    public AuthServiceImpl(AuthenticationManager authenticationManager, SecurityContextRepository securityContextRepository) {
        this.authenticationManager = authenticationManager;
        this.securityContextRepository = securityContextRepository;
    }


    @Override
    public ResponseEntity<Boolean> authenticate(String username, String password, HttpServletRequest request, HttpServletResponse response) {
        ResponseEntity<Boolean> responseEntity = null;

        try {
            UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(username, password);
            Authentication authentication = authenticationManager.authenticate(auth);
            if (authentication.isAuthenticated()) {
                SecurityContext context = securityContextHolderStrategy.createEmptyContext();
                context.setAuthentication(authentication);
                securityContextHolderStrategy.setContext(context);
                securityContextRepository.saveContext(context, request, response);
                responseEntity = ResponseEntity.ok(true);
            }
        } catch (Exception e) {
            throw new BadCredentialsException(e.getMessage());
        }

        return responseEntity;
    }
}
