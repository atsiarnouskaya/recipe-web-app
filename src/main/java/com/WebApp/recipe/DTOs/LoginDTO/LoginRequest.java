package com.WebApp.recipe.DTOs.LoginDTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {
    @NotBlank(message = "Username and password cannot be empty")
    @Size(min = 1, max = 50, message = "Username and password must not be longer than 50 characters")
    @Pattern(regexp = "^[a-zA-Z0-9_-]+$", message = "Only letters, numbers, '_' and '-' are allowed in username")
    private String username;

    @NotBlank(message = "Username and password cannot be empty")
    @Size(min = 1, max = 50, message = "Username and password must not be longer than 50 characters")
    private String password;
}
