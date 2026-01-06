package com.WebApp.recipe.DTOs.LoginDTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class LoginResponse {
    private int id;
    private boolean enabled;
    private String username;
    private String email;
    private String message;
}
