package com.WebApp.recipe.service;

import com.WebApp.recipe.dto.CategoryRequest;
import com.WebApp.recipe.entity.Category;
import com.WebApp.recipe.entity.Ingredient;
import com.WebApp.recipe.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {

    /*CRUD operations are created out of the box by spring boot rest
    POST: Creates a new resource (overridden, "/custom/categories")
    GET: Reads/Retrieve a resource ("/categories", "/categories/{id}")
    PUT: Updates an existing resource ("/categories/{id}")
    DELETE: Deletes a resource ("/categories/{id}")
    */

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /**
     * this method checks if the category exists. If not saves to db.
     * @param category to add
     * @return added or found category
     */
    @Override
    @Transactional
    public CategoryRequest addCategory(Category category) {
        Optional<Category> foundCategory = categoryRepository.findFirstByCategoryName(category.getCategoryName());
        CategoryRequest categoryRequest = new CategoryRequest();
        categoryRequest.setCategoryName(category.getCategoryName());

        if (foundCategory.isEmpty()) {
            category.lowerCaseName();
            categoryRequest.setIngredients(null);
            categoryRepository.save(category);
            return categoryRequest;
        }
        List<String> ingredients = foundCategory.get().getIngredients()
                .stream().map(Ingredient::getName).toList();
        categoryRequest.setIngredients(ingredients);
        return categoryRequest;
    }

    @Override
    @Transactional
    public Category addCategoryServerPurposes(Category category) {
        Optional<Category> foundCategory = categoryRepository.findFirstByCategoryName(category.getCategoryName());

        if (foundCategory.isEmpty()) {
            category.lowerCaseName();
            return categoryRepository.save(category);
        }

        return foundCategory.get();
    }

    @Override
    public Category findFirstByCategoryName(String categoryName) {
        Optional<Category> foundCategory = categoryRepository.findFirstByCategoryName(categoryName);

        if (foundCategory.isEmpty()) {
            throw new RuntimeException("Category not found");
        }

        return foundCategory.get();
    }

}
