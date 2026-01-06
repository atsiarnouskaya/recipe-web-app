package com.WebApp.recipe.Repositories.RecipeRepositories;


import com.WebApp.recipe.Entities.RecipeEntities.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource(path="units")
public interface UnitRepository extends JpaRepository<Unit, Integer> {
    Optional<Unit> findFirstByName(String name);
}

