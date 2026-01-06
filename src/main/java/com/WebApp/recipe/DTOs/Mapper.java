package com.WebApp.recipe.DTOs;

import com.WebApp.recipe.DTOs.CategoryDTOs.CategoryRequest;
import com.WebApp.recipe.DTOs.CategoryDTOs.CategoryResponse;
import com.WebApp.recipe.DTOs.IngredientDTOs.IngredientRequest;
import com.WebApp.recipe.DTOs.IngredientDTOs.IngredientResponse;
import com.WebApp.recipe.DTOs.RecipeDTOs.RecipeRequest;
import com.WebApp.recipe.DTOs.RecipeDTOs.RecipeResponse;
import com.WebApp.recipe.Entities.RecipeEntities.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class Mapper {

    public RecipeResponse toRecipeResponseDTO (Recipe recipe) {
        List<IngredientResponse> ingredients = recipe.getIngredients()
                .stream().map(this::toIngredientResponseDTO).toList();

        return new RecipeResponse(recipe.getId(), recipe.getTitle(), recipe.getShortDescription(),
                                 recipe.getInstructions(), ingredients,
                                 recipe.getVideo().getUrl(), recipe.getUser().getId(), recipe.getUser().getUsername(), null);
    }

    public Recipe toRecipe (RecipeRequest recipeRequest) {
        Video video = new Video();
        video.setUrl(recipeRequest.getVideoURL());
        List<RecipeIngredient> ingredients = new ArrayList<>();
        return new Recipe(recipeRequest.getTitle(),
                          recipeRequest.getShortDescription(),
                          recipeRequest.getSteps(), video, ingredients);
    }

    public IngredientResponse toIngredientResponseDTO (RecipeIngredient ingredient) {
        return new IngredientResponse(ingredient.getIngredient().getId(),
                                     ingredient.getRecipe().getTitle(),
                                     ingredient.getIngredient().getName(),
                                     ingredient.getIngredient().getCategory().getCategoryName(),
                                     ingredient.getAmount(),
                                     ingredient.getUnit().getName()) ;
    }

    public RecipeIngredient toRecipeIngredient (IngredientRequest ingredientRequest) {
        return new RecipeIngredient();
    }

    public Ingredient toIngredient(IngredientRequest ingredientRequest) {
        return new Ingredient(ingredientRequest.getIngredientName());
    }

    public IngredientResponse toIngredientResponse(IngredientRequest ingredientRequest) {
        return new IngredientResponse(0, null, ingredientRequest.getIngredientName(),
                                      ingredientRequest.getCategoryName(), null, null);
    }

    public Category toCategory(IngredientRequest ingredientRequest) {
        return new Category(ingredientRequest.getCategoryName());
    }

    public Category toCategory(CategoryRequest categoryRequest) {
        return new Category(categoryRequest.getCategoryName());
    }

    public CategoryResponse toCategoryResponse(CategoryRequest categoryRequest) {
        return new CategoryResponse(categoryRequest.getCategoryName());
    }
}
