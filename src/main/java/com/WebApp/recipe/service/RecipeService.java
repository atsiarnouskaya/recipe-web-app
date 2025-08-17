package com.WebApp.recipe.service;

import com.WebApp.recipe.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeService extends JpaRepository<Recipe, Integer> {

}
