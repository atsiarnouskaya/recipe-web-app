package com.WebApp.recipe.controller;

import com.WebApp.recipe.dto.IngredientDTOs.IngredientResponse;
import com.WebApp.recipe.dto.Mapper;
import com.WebApp.recipe.dto.RecipeDTOs.RecipeRequest;
import com.WebApp.recipe.dto.RecipeDTOs.RecipeResponse;
import com.WebApp.recipe.entity.*;
import com.WebApp.recipe.repository.UnitRepository;
import com.WebApp.recipe.service.CategoryService;
import com.WebApp.recipe.service.IngredientService;
import com.WebApp.recipe.service.RecipeService;
import com.WebApp.recipe.service.UnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/custom")
public class RecipeController {

    private final RecipeService recipeService;
    private final IngredientService ingredientService;
    private final UnitRepository unitRepository;
    private final CategoryService categoryService;
    private final UnitService unitService;
    private final Mapper mapper;

    @Autowired
    public RecipeController(RecipeService recipeService, IngredientService ingredientService,
                            Mapper mapper, UnitRepository unitRepository,
                            CategoryService categoryService, UnitService unitService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.unitRepository = unitRepository;
        this.categoryService = categoryService;
        this.unitService = unitService;
        this.mapper = mapper;
    }

    @PostMapping("/addRecipe")
    public RecipeResponse addRecipe(@RequestBody RecipeRequest recipeRequest) {

        Recipe recipe = mapper.toRecipe(recipeRequest);
        recipeService.save(recipe);


        for (var ingredient : recipeRequest.getIngredients()) {

            //create and find an ingredient in the db
            Ingredient ingredientFromUser = new Ingredient(ingredient.getIngredientName());
            Ingredient ingFromDB = ingredientService.addIngredient(ingredientFromUser); //get ingr with id

            //create and find an ingredient in the db
            Category categoryToFind = new Category(ingredient.getCategoryName());
            Category categoryFromUser = categoryService.addCategoryServerPurposes(categoryToFind);

            //set a category for a found ingredient
            ingFromDB.setCategory(categoryFromUser);

            Unit unit = new Unit(ingredient.getEndUnit());
            double amount;

            if (!ingredient.getStartUnit().equals(ingredient.getEndUnit())) {
                unit = unitService.findByNameElseAdd(unit);
                amount = unitService.convertToAnyUnit(
                        ingredient.getStartUnit(),
                        ingredient.getEndUnit(),
                        ingredient.getAmount());
            } else {
                unit = unitService.findByNameElseAdd(unit);
                amount = ingredient.getAmount();
            }

            amount = unitService.adjustAmount(amount, ingredient.getAdjustingFactor());

            RecipeIngredient recipeIngredient = new RecipeIngredient(recipe, ingFromDB,
                    amount, unit);

            recipe.addIngredient(recipeIngredient);
        }

        recipeService.save(recipe);
        return mapper.toRecipeResponseDTO(recipe);
    }
}
