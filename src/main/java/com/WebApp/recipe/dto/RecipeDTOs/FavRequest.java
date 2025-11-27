package com.WebApp.recipe.dto.RecipeDTOs;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class FavRequest {
    private int recipeId;
    private boolean isLiked;
}
