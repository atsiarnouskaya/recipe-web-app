package com.WebApp.recipe.Controllers.RecipeControlles;

import com.WebApp.recipe.DTOs.CategoryDTOs.CategoryRequest;
import com.WebApp.recipe.DTOs.CategoryDTOs.CategoryResponse;
import com.WebApp.recipe.DTOs.Mapper;
import com.WebApp.recipe.Services.RecipeSevices.CategoryService;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("custom")
public class CategoryController {

    private final CategoryService categoryService;
    private final Mapper mapper;

    public CategoryController(CategoryService categoryService, Mapper mapper) {
        this.categoryService = categoryService;
        this.mapper = mapper;
    }

    @PutMapping("/softDelete")
    public CategoryResponse deleteCategory(@RequestBody CategoryRequest categoryRequest) {
        categoryService.deleteCategory(categoryRequest);

        return mapper.toCategoryResponse(categoryRequest);
    }

}
