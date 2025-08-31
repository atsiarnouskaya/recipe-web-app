package com.WebApp.recipe.controller;

import com.WebApp.recipe.dto.IngredientDTOs.IngredientRequest;
import com.WebApp.recipe.dto.IngredientDTOs.IngredientResponse;
import com.WebApp.recipe.dto.Mapper;
import com.WebApp.recipe.entity.Category;
import com.WebApp.recipe.entity.Ingredient;
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
    private final Mapper mapper;

    @Autowired
    public IngredientController(IngredientService ingredientService,
                                Mapper mapper) {
        this.ingredientService = ingredientService;
        this.mapper = mapper;
    }

    @PostMapping("/ingredients")
    public IngredientResponse addIngredient(@RequestBody IngredientRequest request) {
        Ingredient ingredient = mapper.toIngredient(request);
        Category category = mapper.toCategory(request);
        ingredient.setCategory(category);
        ingredientService.addIngredient(ingredient);
        return mapper.toIngredientResponse(request);
    }
}
