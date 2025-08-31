package com.WebApp.recipe.service;

import com.WebApp.recipe.dto.IngredientDTOs.IngredientRequest;
import com.WebApp.recipe.dto.RecipeDTOs.RecipeRequest;
import com.WebApp.recipe.dto.RecipeDTOs.RecipeResponse;
import com.WebApp.recipe.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RecipeService {
    RecipeResponse getRecipeById(int id);

    List<RecipeResponse> getRecipes();

    RecipeResponse updateRecipe(int id, RecipeRequest recipeRequest);

    RecipeResponse save(RecipeRequest recipeRequest);

    Recipe findById(int id);

    List<RecipeResponse> getRecipesByIngredients(List<IngredientRequest> ingredients);
}
