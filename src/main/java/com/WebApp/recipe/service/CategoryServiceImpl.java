package com.WebApp.recipe.service;

import com.WebApp.recipe.dto.CategoryDTOs.CategoryRequest;
import com.WebApp.recipe.dto.CategoryDTOs.CategoryResponse;
import com.WebApp.recipe.dto.Mapper;
import com.WebApp.recipe.entity.Category;
import com.WebApp.recipe.entity.Ingredient;
import com.WebApp.recipe.entity.Recipe;
import com.WebApp.recipe.repository.CategoryRepository;
import com.WebApp.recipe.repository.IngredientRepository;
import com.WebApp.recipe.repository.RecipeRepository;
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
    private final IngredientRepository ingredientRepository;
    private final RecipeRepository recipeRepository;
    private final Mapper mapper;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository, IngredientRepository ingredientRepository, RecipeRepository recipeRepository, Mapper mapper) {
        this.categoryRepository = categoryRepository;
        this.ingredientRepository = ingredientRepository;
        this.recipeRepository = recipeRepository;
        this.mapper = mapper;
    }

    /**
     * this method checks if the category exists. If not saves to db.
     * @param categoryRequest to add
     * @return added or found category
     */
    @Override
    @Transactional
    public CategoryResponse addCategory(CategoryRequest categoryRequest) {

        Optional<Category> foundCategory = categoryRepository.findFirstByCategoryName(categoryRequest.getCategoryName());
        CategoryResponse categoryResponse = new CategoryResponse();
        categoryResponse.setCategoryName(categoryRequest.getCategoryName());

        if (foundCategory.isEmpty()) {
            Category category = mapper.toCategory(categoryRequest);
            category.lowerCaseName();
            categoryResponse.setIngredients(null);
            categoryRepository.save(category);
            return categoryResponse;
        }
        else {
            Category category = foundCategory.get();
            category.setDeleted(false);
        }

        List<String> ingredients = foundCategory.get().getIngredients()
                .stream().map(Ingredient::getName).toList();
        categoryResponse.setIngredients(ingredients);

        return categoryResponse;
    }

    @Override
    @Transactional
    public void deleteCategory(CategoryRequest categoryRequest) {
        Optional<Category> foundCategory = categoryRepository.findFirstByCategoryName(categoryRequest.getCategoryName());

        Category categoryToDelete;
        if (foundCategory.isPresent()) {
            categoryToDelete = foundCategory.get();

            List<Ingredient> ingredients = ingredientRepository.findAllByCategoryId(categoryToDelete.getId());

            for (Ingredient ingredient : ingredients) {
                ingredient.deleteIngredient();
                ingredientRepository.save(ingredient);
            }

            List<Integer> ingredientIds = ingredients.stream().map(Ingredient::getId).toList();

            List<Recipe> recipes = recipeRepository.findAllContainingIngredients(ingredientIds, ingredientIds.size());

            for (Recipe recipe : recipes) {
                recipe.deleteRecipe();
            }

            categoryToDelete.deleteCategory();
            categoryRepository.save(categoryToDelete);
        }
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
