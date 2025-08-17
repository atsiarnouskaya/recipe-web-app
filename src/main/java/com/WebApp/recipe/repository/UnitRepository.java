package com.WebApp.recipe.repository;


import com.WebApp.recipe.entity.Unit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path="units")
public interface UnitRepository extends JpaRepository<Unit, Integer> {
    Unit findByName(String unit);
}
