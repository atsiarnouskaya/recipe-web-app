package com.WebApp.recipe.Repositories.RecipeRepositories;

import com.WebApp.recipe.Entities.RecipeEntities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(path="categories")
@CrossOrigin(origins = "http://localhost:3000")
@Repository
public interface CategoryRepository extends JpaRepository<Category, Integer> {

    Optional<Category> findFirstByCategoryName(String categoryName);

    List<Category> findAll();

}
