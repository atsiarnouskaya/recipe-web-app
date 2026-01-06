package com.WebApp.recipe.DTOs.RecipeDTOs;

import com.WebApp.recipe.DTOs.IngredientDTOs.IngredientResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RecipeResponse {

    private int id;
    private String title;
    private String shortDescription;
    private String steps;
    private List<IngredientResponse> ingredients;
    private String videoURL;

    private int userId;
    private String username;

    private String message;
}
