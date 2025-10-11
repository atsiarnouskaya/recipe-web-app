package com.WebApp.recipe.Security.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<Boolean> authenticate (String username, String password,
                                          HttpServletRequest request, HttpServletResponse response);
}
