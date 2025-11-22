package com.WebApp.recipe.Security.DTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponse {
    private int id;
    private String username;
    private String message;

    public UserResponse(int id, String username) {
        this.id = id;
        this.username = username;
        this.message = "";
    }
}
