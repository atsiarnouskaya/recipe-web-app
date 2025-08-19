package com.WebApp.recipe.controller;

import com.WebApp.recipe.dto.IngredientDTOs.IngredientRequest;
import com.WebApp.recipe.entity.Category;
import com.WebApp.recipe.entity.Ingredient;
import com.WebApp.recipe.service.CategoryService;
import com.WebApp.recipe.service.IngredientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("custom")
public class IngredientController {

    private final IngredientService ingredientService;
    private final CategoryService categoryService;

    @Autowired
    public IngredientController(IngredientService ingredientService, CategoryService categoryService) {
        this.ingredientService = ingredientService;
        this.categoryService = categoryService;
    }

//    @PostMapping("/ingredients")
//    public IngredientRequest addIngredient(@RequestBody IngredientRequest request) {
//        Ingredient ingredient = new Ingredient(request.getIngredientName());
//        Category category = new Category(request.getCategoryName());
//        ingredient.setCategory(category);
//        Ingredient saved = ingredientService.addIngredient(ingredient);
//        return new IngredientRequest(null, saved.getName(), request.getCategoryName(), null, null);
//    }
}
