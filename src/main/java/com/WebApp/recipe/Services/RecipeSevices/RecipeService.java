package com.WebApp.recipe.Services.RecipeSevices;

import com.WebApp.recipe.DTOs.IngredientDTOs.IngredientRequest;
import com.WebApp.recipe.DTOs.RecipeDTOs.RecipeRequest;
import com.WebApp.recipe.DTOs.RecipeDTOs.RecipeResponse;
import com.WebApp.recipe.Entities.RecipeEntities.Recipe;

import java.util.List;

public interface RecipeService {
    RecipeResponse getRecipeById(int id);

    List<RecipeResponse> getRecipes();

    RecipeResponse updateRecipe(int id, RecipeRequest recipeRequest);

    RecipeResponse save(RecipeRequest recipeRequest);

    Recipe findById(int id);

    List<RecipeResponse> getRecipesByIngredients(List<IngredientRequest> ingredients);

    RecipeResponse deleteRecipe(int id, String username);

    List<RecipeResponse> getRecipesByAuthor(int userId);

    List<RecipeResponse> userFavRecipes(int userId);

    RecipeResponse likeRecipe(int recipeId, String username);

    RecipeResponse dislikeRecipe(int recipeId, String username);
}
