package com.WebApp.recipe.service;

import com.WebApp.recipe.dto.CategoryRequest;
import com.WebApp.recipe.entity.Category;

import java.util.List;

public interface CategoryService {

    CategoryRequest addCategory(Category category);

    Category findFirstByCategoryName(String categoryName);
}
