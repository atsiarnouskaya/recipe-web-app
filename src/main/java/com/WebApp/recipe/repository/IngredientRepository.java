package com.WebApp.recipe.repository;

import com.WebApp.recipe.entity.Ingredient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(path = "ingredients")
public interface IngredientRepository extends JpaRepository<Ingredient, Integer> {

    Optional<Ingredient> findFirstByName(String name);

    @Query(value = "SELECT i.* FROM ingredients i " +
            "WHERE i.category_id = :categoryId", nativeQuery = true)
    List<Ingredient> findAllByCategoryId(@Param("categoryId") Integer categoryId);

    Optional<Ingredient> findIngredientByName(String name);
}
