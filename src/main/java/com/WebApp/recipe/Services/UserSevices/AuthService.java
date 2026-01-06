package com.WebApp.recipe.Services.UserSevices;

import com.WebApp.recipe.Exceptions.UserAlreadyExistsException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.ResponseEntity;

public interface AuthService {
    ResponseEntity<Boolean> authenticate (String username, String password,
                                          HttpServletRequest request, HttpServletResponse response) throws UserAlreadyExistsException;
}
