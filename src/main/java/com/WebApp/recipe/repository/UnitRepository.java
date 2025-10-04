package com.WebApp.recipe.repository;


import com.WebApp.recipe.entity.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource(path="units")
public interface UnitRepository extends JpaRepository<Unit, Integer> {
    Optional<Unit> findByName(String unit);

    Optional<Unit> findFirstByName(String name);
}

