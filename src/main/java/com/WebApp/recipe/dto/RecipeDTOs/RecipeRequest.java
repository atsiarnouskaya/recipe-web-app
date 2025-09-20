package com.WebApp.recipe.dto.RecipeDTOs;

import com.WebApp.recipe.dto.IngredientDTOs.IngredientRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RecipeRequest {

    private String title;
    private String username;
    private String shortDescription;
    private String steps;
    private List<IngredientRequest> ingredients;
    private String videoURL;

}
