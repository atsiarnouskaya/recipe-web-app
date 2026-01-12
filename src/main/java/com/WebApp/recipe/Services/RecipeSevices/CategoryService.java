package com.WebApp.recipe.Services.RecipeSevices;

import com.WebApp.recipe.DTOs.CategoryDTOs.CategoryRequest;
import com.WebApp.recipe.DTOs.CategoryDTOs.CategoryResponse;
import com.WebApp.recipe.Entities.RecipeEntities.Category;

import java.util.List;

public interface CategoryService {

    CategoryResponse addCategory(CategoryRequest categoryRequest);

    Category findFirstByCategoryName(String categoryName);

    void deleteCategory(CategoryRequest categoryRequest);

    List<CategoryResponse> getAllCategories();
}
