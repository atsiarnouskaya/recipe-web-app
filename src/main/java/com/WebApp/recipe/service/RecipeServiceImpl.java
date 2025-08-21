package com.WebApp.recipe.service;

import com.WebApp.recipe.dto.IngredientDTOs.IngredientRequest;
import com.WebApp.recipe.dto.Mapper;
import com.WebApp.recipe.dto.RecipeDTOs.RecipeResponse;
import com.WebApp.recipe.entity.Ingredient;
import com.WebApp.recipe.entity.Recipe;
import com.WebApp.recipe.repository.IngredientRepository;
import com.WebApp.recipe.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RecipeServiceImpl implements RecipeService {

    private final RecipeRepository recipeRepository;
    private final IngredientRepository ingredientRepository;
    private final Mapper mapper;

    @Autowired
    public RecipeServiceImpl(RecipeRepository recipeRepository,
                             IngredientRepository ingredientRepository,
                             Mapper mapper) {
        this.recipeRepository = recipeRepository;
        this.ingredientRepository = ingredientRepository;
        this.mapper = mapper;
    }

      /*
    POST: Creates a new resource (overridden, "/custom/addRecipe")
    GET: Reads/Retrieve a resource (overridden, "/custom/recipes", "/custom/recipes/{id}")
    PUT: Updates an existing resource ("/recipes/{id}")
    DELETE: Deletes a resource ("/recipes/{id}")
    */

    @Override
    public RecipeResponse getRecipeById(int id) {
        Recipe recipe = recipeRepository.findById(id).orElse(null);
        if (recipe == null) {
            return null;
        }
        return mapper.toRecipeResponseDTO(recipe);
    }

    @Override
    public List<RecipeResponse> getRecipes() {
        List<Recipe> recipes = recipeRepository.findAll();
        List<RecipeResponse> recipeResponses = new ArrayList<>();
        for (Recipe recipe : recipes) {
            recipeResponses.add(mapper.toRecipeResponseDTO(recipe));
        }
        return recipeResponses;
    }

    @Override
    @Transactional
    public void save(Recipe recipe) {
        recipeRepository.save(recipe);
    }

    @Override
    public Recipe findById(int id) {
        return recipeRepository.findById(id).orElse(null);
    }

    @Override
    public List<RecipeResponse> getRecipesByIngredients(List<IngredientRequest> ingredients) {
        List<RecipeResponse> recipeResponses = new ArrayList<>();
        List<Recipe> recipes;
        List<Integer> ingredientIds = new ArrayList<>();

        for (IngredientRequest ingredient : ingredients) {
            Optional<Ingredient> optionalIngredient = ingredientRepository.findFirstByName(ingredient.getIngredientName());
            if (optionalIngredient.isPresent()) {
                ingredientIds.add(optionalIngredient.get().getId());
            }
        }

        recipes = recipeRepository.findAllContainingIngredients(ingredientIds, ingredientIds.size());

        for (Recipe recipe : recipes) {
            recipeResponses.add(mapper.toRecipeResponseDTO(recipe));
        }

        return recipeResponses;
    }

}
