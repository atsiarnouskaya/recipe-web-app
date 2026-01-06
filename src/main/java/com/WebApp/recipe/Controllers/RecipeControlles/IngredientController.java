package com.WebApp.recipe.Controllers.RecipeControlles;

import com.WebApp.recipe.DTOs.IngredientDTOs.IngredientRequest;
import com.WebApp.recipe.DTOs.IngredientDTOs.IngredientResponse;
import com.WebApp.recipe.DTOs.Mapper;
import com.WebApp.recipe.Entities.RecipeEntities.Category;
import com.WebApp.recipe.Entities.RecipeEntities.Ingredient;
import com.WebApp.recipe.Services.RecipeSevices.IngredientService;
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
