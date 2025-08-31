package com.WebApp.recipe.controller;

import com.WebApp.recipe.dto.CategoryDTOs.CategoryRequest;
import com.WebApp.recipe.dto.CategoryDTOs.CategoryResponse;
import com.WebApp.recipe.dto.Mapper;
import com.WebApp.recipe.entity.Category;
import com.WebApp.recipe.service.CategoryService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("custom")
public class CategoryController {

    private final CategoryService categoryService;
    private final Mapper mapper;

    public CategoryController(CategoryService categoryService, Mapper mapper) {
        this.categoryService = categoryService;
        this.mapper = mapper;
    }

    @PostMapping("/categories")
    public CategoryResponse addCategory(@RequestBody CategoryRequest categoryRequest) {
        return categoryService.addCategory(categoryRequest);
    }

    @PutMapping("/softDelete")
    public CategoryResponse deleteCategory(@RequestBody CategoryRequest categoryRequest) {
        categoryService.deleteCategory(categoryRequest);

        return mapper.toCategoryResponse(categoryRequest);
    }

}
