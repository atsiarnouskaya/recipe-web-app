package com.WebApp.recipe.repository;

import com.WebApp.recipe.entity.Recipe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(path="recipes")
public interface RecipeRepository extends JpaRepository<Recipe, Integer> {

    @Query(value = "SELECT r.* FROM recipes r " +
            "JOIN recipes_ingredients ri ON r.id = ri.recipe_id " +
            "WHERE ri.ingredient_id IN :ingredientIds " +
            "GROUP BY r.id " +
            "HAVING COUNT(DISTINCT ri.ingredient_id) = :ingredientsAmount",
            nativeQuery = true)
    List<Recipe> findAllContainingIngredients(
            @Param("ingredientIds") List<Integer> ingredientIds,
            @Param("ingredientsAmount") Integer ingredientsAmount);
}
