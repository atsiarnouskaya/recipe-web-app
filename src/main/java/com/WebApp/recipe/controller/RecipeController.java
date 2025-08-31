package com.WebApp.recipe.controller;

import com.WebApp.recipe.dto.IngredientDTOs.IngredientRequest;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/custom")
public class RecipeController {

    private final RecipeService recipeService;

    @Autowired
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @PostMapping("/addRecipe")
    public RecipeResponse addRecipe(@RequestBody RecipeRequest recipeRequest) {
        return recipeService.save(recipeRequest);
    }

    @GetMapping("/recipe/{id}")
    public RecipeResponse getRecipe(@PathVariable int id) {
        return recipeService.getRecipeById(id);
    }

    @GetMapping("/recipes")
    public List<RecipeResponse> getAllRecipes() {
        return recipeService.getRecipes();
    }

    @PutMapping("/recipes/{id}")
    public RecipeResponse updateRecipe(@PathVariable int id, @RequestBody RecipeRequest recipeRequest) {
        return recipeService.updateRecipe(id, recipeRequest);
    }

    @GetMapping("/getRecipesByIngredients")
    public List<RecipeResponse> getAllRecipesByIngredients(@RequestBody List<IngredientRequest> ingredients) {
        return recipeService.getRecipesByIngredients(ingredients);
    }

}
