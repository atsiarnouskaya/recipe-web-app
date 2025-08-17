package com.WebApp.recipe.dto;

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
    private String shortDescription;
    private String steps;
    private List<IngredientRequest> ingredients;
    private String videoURL;

}
