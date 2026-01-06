package com.WebApp.recipe.Events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EmailVerificationEvent {
    private String email;
    private String token;
}