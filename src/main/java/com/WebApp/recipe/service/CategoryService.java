package com.WebApp.recipe.service;

import com.WebApp.recipe.dto.CategoryDTOs.CategoryRequest;
import com.WebApp.recipe.dto.CategoryDTOs.CategoryResponse;
import com.WebApp.recipe.entity.Category;

public interface CategoryService {

    CategoryResponse addCategory(CategoryRequest categoryRequest);

    Category findFirstByCategoryName(String categoryName);

    void deleteCategory(CategoryRequest categoryRequest);
}
