package com.WebApp.recipe.service;

import com.WebApp.recipe.Security.entity.User;
import com.WebApp.recipe.dto.IngredientDTOs.IngredientRequest;
import com.WebApp.recipe.dto.RecipeDTOs.RecipeRequest;
import com.WebApp.recipe.dto.RecipeDTOs.RecipeResponse;
import com.WebApp.recipe.entity.Recipe;

import java.util.List;

public interface RecipeService {
    RecipeResponse getRecipeById(int id);

    List<RecipeResponse> getRecipes();

    RecipeResponse updateRecipe(int id, RecipeRequest recipeRequest);

    RecipeResponse save(RecipeRequest recipeRequest);

    Recipe findById(int id);

    List<RecipeResponse> getRecipesByIngredients(List<IngredientRequest> ingredients);

    RecipeResponse deleteRecipe(int id);

    List<RecipeResponse> getRecipesByAuthor(int userId);

    List<RecipeResponse> userFavRecipes(int userId);

    RecipeResponse likeRecipe(int recipeId, int userId);
}
