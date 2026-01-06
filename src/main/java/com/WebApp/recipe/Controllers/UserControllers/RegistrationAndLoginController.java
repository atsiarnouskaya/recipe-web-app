package com.WebApp.recipe.Controllers.UserControllers;

import com.WebApp.recipe.DTOs.LoginDTO.LoginRequest;
import com.WebApp.recipe.DTOs.LoginDTO.LoginResponse;
import com.WebApp.recipe.DTOs.UserDTOs.UserRequest;
import com.WebApp.recipe.DTOs.UserDTOs.UserResponse;
import com.WebApp.recipe.Exceptions.UserAlreadyExistsException;
import com.WebApp.recipe.Services.UserSevices.AuthService;
import com.WebApp.recipe.Services.UserSevices.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.web.bind.annotation.*;


@RestController
public class RegistrationAndLoginController {

    private final UserService userService;
    private final AuthService authService;

    @Autowired
    public RegistrationAndLoginController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }


    @PostMapping("/signup")
    public ResponseEntity<UserResponse> registerUser(@Valid @RequestBody UserRequest userRequest) {
        UserResponse userResponse;
        try {
            userResponse = userService.signUpUser(userRequest);
        }
        catch (UserAlreadyExistsException | IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new UserResponse(0, userRequest.getUsername(), e.getMessage(), userRequest.getEmail()));
        }

        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }

    @PostMapping("/signin")
    public ResponseEntity<LoginResponse> loginUser(@Valid @RequestBody LoginRequest loginRequest,
                                                   HttpServletRequest request,
                                                   HttpServletResponse response) throws UsernameNotFoundException {

        try {
            ResponseEntity<Boolean> isAuthenticated = authService.authenticate(loginRequest.getUsername(), loginRequest.getPassword(), request, response);
            if (Boolean.TRUE.equals(isAuthenticated.getBody())) {
                UserResponse user = userService.getUserByUsername(loginRequest.getUsername());

                return ResponseEntity.ok(new LoginResponse(user.getId(), true, user.getUsername(), user.getEmail(), null));
            }
        } catch (UserAlreadyExistsException e) {
            UserResponse user = userService.getUserByUsername(loginRequest.getUsername());
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body((new LoginResponse(user.getId(), true, user.getUsername(), user.getEmail(), e.getMessage())));
        } catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    @GetMapping("/logoutSuccess")
    public String logoutSuccess() {
        return "logoutSuccess";
    }


    @PostMapping("/logout")
    public ResponseEntity<Void> logout(HttpSession session) {
        System.out.println("Logging out");
        if (session != null) {
            session.invalidate();
        }

        return ResponseEntity.noContent().build();
    }

}
