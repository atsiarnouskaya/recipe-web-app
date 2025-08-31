package com.WebApp.recipe.repository;

import com.WebApp.recipe.entity.Ingredient;
import com.WebApp.recipe.entity.Recipe;
import com.WebApp.recipe.entity.RecipeIngredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource(path="RecipeIngredient")
public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredient, Integer> {
    Optional<RecipeIngredient> findByRecipeAndIngredient(Recipe recipe, Ingredient ingredient);
}
