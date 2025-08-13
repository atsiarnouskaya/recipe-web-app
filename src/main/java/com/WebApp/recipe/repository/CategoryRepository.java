package com.WebApp.recipe.repository;

import com.WebApp.recipe.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Optional;

@RepositoryRestResource(path="categories")
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    Optional<Category> findFirstByCategoryName(String categoryName);

    Optional<Category> findCategoryById(int categoryId);
}
