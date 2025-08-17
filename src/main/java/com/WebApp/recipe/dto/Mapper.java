package com.WebApp.recipe.dto;

import com.WebApp.recipe.entity.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Mapper {

    public RecipeRequest toRecipeDto (Recipe recipe) {
        List<IngredientRequest> ingredients = recipe.getIngredients()
                .stream().map(this::toIngredientRequestDto).toList();

        return new RecipeRequest(recipe.getTitle(), recipe.getShortDescription(),
                                 recipe.getInstructions(), ingredients,
                                 recipe.getVideo().getUrl());
    }

    public Recipe toRecipe (RecipeRequest recipeRequest) {
        Video video = new Video();
        video.setUrl(recipeRequest.getVideoURL());
        List<RecipeIngredient> ingredients = new ArrayList<>();
        return new Recipe(recipeRequest.getTitle(),
                          recipeRequest.getShortDescription(),
                          recipeRequest.getSteps(), video, ingredients);
    }

    public IngredientRequest toIngredientRequestDto (RecipeIngredient ingredient) {
        return new IngredientRequest(ingredient.getRecipe().getTitle(),
                                     ingredient.getIngredient().getName(),
                                     ingredient.getIngredient().getCategory().getCategoryName(),
                                     ingredient.getAmount(),
                                     ingredient.getUnit().getName()) ;
    }

    public RecipeIngredient toRecipeIngredient (IngredientRequest ingredientRequest) {
        //Ingredient ingredient = new Ingredient(ingredientRequest.getIngredientName());
        Unit unit = new Unit(ingredientRequest.getUnit());

        return new RecipeIngredient(null, null,
                ingredientRequest.getAmount(), unit);
    }
}
