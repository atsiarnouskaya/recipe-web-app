package com.WebApp.recipe.controller;

import com.WebApp.recipe.entity.Category;
import com.WebApp.recipe.service.CategoryService;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("custom")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/categories")
    public Category addCategory(@RequestBody Category category) {
        return categoryService.addCategory(category);
    }

//    @GetMapping("/categories/{id}")
//    public Category getCategory(@PathVariable Integer id) {
//        return categoryService.getCategoryById(id);
//    }
}
