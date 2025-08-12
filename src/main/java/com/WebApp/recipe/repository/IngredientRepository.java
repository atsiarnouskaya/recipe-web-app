package com.WebApp.recipe.repository;

import com.WebApp.recipe.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "ingredients")
public interface IngredientRepository extends JpaRepository<Ingredient, Integer> {
}
