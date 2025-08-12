package com.WebApp.recipe.repository;

import com.WebApp.recipe.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path="recipes")
public interface RecipeRepository extends JpaRepository<Recipe, Integer> {
}
