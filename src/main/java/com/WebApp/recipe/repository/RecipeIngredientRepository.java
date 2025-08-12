package com.WebApp.recipe.repository;

import com.WebApp.recipe.entity.RecipeIngredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path="RecipeIngredient")
public interface RecipeIngredientRepository extends JpaRepository<RecipeIngredient, Integer> {
}
