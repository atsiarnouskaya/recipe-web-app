package com.WebApp.recipe.DTOs.UserDTOs;

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
    private boolean isVerified;
    private String username;
    private String message;
    private String email;

    public UserResponse(int id, String username) {
        this.id = id;
        this.username = username;
        this.message = "";
    }

    public UserResponse(int id, String username, String message, String email) {
        this.id = id;
        this.username = username;
        this.message = message;
        this.email = email;
    }

    public UserResponse(int id, String username, String email) {
        this.id = id;
        this.username = username;
        this.email = email;
    }
}
