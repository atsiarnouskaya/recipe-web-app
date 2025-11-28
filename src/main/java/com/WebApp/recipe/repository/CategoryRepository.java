package com.WebApp.recipe.repository;

import com.WebApp.recipe.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Optional;

@RepositoryRestResource(path="categories")
@CrossOrigin(origins = "http://localhost:3000")
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    Optional<Category> findFirstByCategoryName(String categoryName);

}
