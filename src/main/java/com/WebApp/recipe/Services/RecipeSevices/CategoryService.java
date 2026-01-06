package com.WebApp.recipe.Services.RecipeSevices;

import com.WebApp.recipe.DTOs.CategoryDTOs.CategoryRequest;
import com.WebApp.recipe.DTOs.CategoryDTOs.CategoryResponse;
import com.WebApp.recipe.Entities.RecipeEntities.Category;

public interface CategoryService {

    CategoryResponse addCategory(CategoryRequest categoryRequest);

    Category findFirstByCategoryName(String categoryName);

    void deleteCategory(CategoryRequest categoryRequest);
}
