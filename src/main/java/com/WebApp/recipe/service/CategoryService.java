package com.WebApp.recipe.service;

import com.WebApp.recipe.dto.CategoryDTOs.CategoryRequest;
import com.WebApp.recipe.entity.Category;

public interface CategoryService {

    CategoryRequest addCategory(Category category);

    Category findFirstByCategoryName(String categoryName);

    Category addCategoryServerPurposes(Category category);
}
