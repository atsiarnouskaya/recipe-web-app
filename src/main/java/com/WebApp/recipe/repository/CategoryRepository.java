package com.WebApp.recipe.repository;

import com.WebApp.recipe.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path="categories")
public interface CategoryRepository extends JpaRepository<Category, Integer> {
}
