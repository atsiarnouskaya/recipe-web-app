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
    private final IngredientService ingredientService;
    private final CategoryService categoryService;
    private final UnitService unitService;
    private final Mapper mapper;

    @Autowired
    public RecipeController(RecipeService recipeService, IngredientService ingredientService,
                            Mapper mapper, CategoryService categoryService,
                            UnitService unitService) {
        this.recipeService = recipeService;
        this.ingredientService = ingredientService;
        this.categoryService = categoryService;
        this.unitService = unitService;
        this.mapper = mapper;
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
          RecipeResponse recipeResponse = recipeService.updateRecipe(id, recipeRequest);
//        Recipe recipeToEdit = recipeService.findById(id);
//        Recipe recipe = mapper.toRecipe(recipeRequest);
//
//        recipeToEdit.setTitle(recipe.getTitle());
//        recipeToEdit.setShortDescription(recipe.getShortDescription());
//        recipeToEdit.setInstructions(recipe.getInstructions());
//        recipeToEdit.setVideo(recipe.getVideo());
//
//        recipeToEdit.getIngredients().clear();
//
//        for (IngredientRequest ingredient : recipeRequest.getIngredients()) {
//
//            addIngredientsToRecipe(ingredient, recipe);
//
//        }
//
//        recipeService.save(recipeToEdit);

        return recipeResponse;
    }

    @GetMapping("/getRecipesByIngredients")
    public List<RecipeResponse> getAllRecipesByIngredients(@RequestBody List<IngredientRequest> ingredients) {
        return recipeService.getRecipesByIngredients(ingredients);
    }

}
