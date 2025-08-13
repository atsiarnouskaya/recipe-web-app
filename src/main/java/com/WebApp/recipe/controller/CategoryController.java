package com.WebApp.recipe.controller;

import com.WebApp.recipe.dto.CategoryRequest;
import com.WebApp.recipe.entity.Category;
import com.WebApp.recipe.service.CategoryService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("custom")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/categories")
    public CategoryRequest addCategory(@RequestBody Category category) {
        return categoryService.addCategory(category);
    }
}
