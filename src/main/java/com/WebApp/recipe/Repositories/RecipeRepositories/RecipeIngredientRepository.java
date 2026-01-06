package com.WebApp.recipe.Repositories.RecipeRepositories;

import com.WebApp.recipe.Entities.RecipeEntities.Ingredient;
import com.WebApp.recipe.Entities.RecipeEntities.Recipe;
import com.WebApp.recipe.Entities.RecipeEntities.RecipeIngredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource(path="RecipeIngredient")
public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredient, Integer> {
    Optional<RecipeIngredient> findByRecipeAndIngredient(Recipe recipe, Ingredient ingredient);
}
