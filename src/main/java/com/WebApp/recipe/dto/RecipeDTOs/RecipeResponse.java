package com.WebApp.recipe.dto.RecipeDTOs;

import com.WebApp.recipe.dto.IngredientDTOs.IngredientResponse;
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

    private String title;
    private String shortDescription;
    private String steps;
    private List<IngredientResponse> ingredients;
    private String videoURL;

}
