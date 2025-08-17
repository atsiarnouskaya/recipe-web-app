package com.WebApp.recipe.controller;

import com.WebApp.recipe.dto.Mapper;
import com.WebApp.recipe.dto.RecipeRequest;
import com.WebApp.recipe.entity.*;
import com.WebApp.recipe.repository.UnitRepository;
import com.WebApp.recipe.service.CategoryService;
import com.WebApp.recipe.service.IngredientService;
import com.WebApp.recipe.service.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/custom")
public class RecipeController {

    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final UnitRepository unitRepository;
    private final CategoryService categoryService;
    private final Mapper mapper;

    @Autowired
    public RecipeController(RecipeService recipeService, IngredientService ingredientService,
                            Mapper mapper, UnitRepository unitRepository,
                            CategoryService categoryService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.unitRepository = unitRepository;
        this.categoryService = categoryService;
        this.mapper = mapper;
    }

    @PostMapping("/addRecipe")
    public RecipeRequest addRecipe(@RequestBody RecipeRequest recipeRequest) {
        Recipe recipe = mapper.toRecipe(recipeRequest);
        recipeService.save(recipe);


        for (var ingredient : recipeRequest.getIngredients()) {
            Ingredient ingredientFromUser = new Ingredient(ingredient.getIngredientName());
            Category categoryToFind = new Category(ingredient.getCategoryName());
            Category categoryFromUser = categoryService.addCategoryServerPurposes(categoryToFind);

            Ingredient ingFromDB = ingredientService.addIngredient(ingredientFromUser); //get ingr with id
            ingFromDB.setCategory(categoryFromUser);

            Unit unit = unitRepository.findByName(ingredient.getUnit());

            RecipeIngredient recipeIngredient = new RecipeIngredient(recipe, ingFromDB,
                    ingredient.getAmount(), unit);
            recipe.addIngredient(recipeIngredient);
        }

        return mapper.toRecipeDto(recipe);
    }
}
