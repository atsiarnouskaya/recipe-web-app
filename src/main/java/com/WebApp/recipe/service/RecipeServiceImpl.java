package com.WebApp.recipe.service;

import com.WebApp.recipe.dto.IngredientDTOs.IngredientRequest;
import com.WebApp.recipe.dto.Mapper;
import com.WebApp.recipe.dto.RecipeDTOs.RecipeRequest;
import com.WebApp.recipe.dto.RecipeDTOs.RecipeResponse;
import com.WebApp.recipe.entity.*;
import com.WebApp.recipe.repository.*;
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
    private final RecipeIngredientRepository recipeIngredientRepository;
    private final VideoRepository videoRepository;
    private final IngredientService ingredientService;
    private final CategoryService categoryService;
    private final UnitService unitService;
    private final CategoryRepository categoryRepository;
    private final Mapper mapper;

    @Autowired
    public RecipeServiceImpl(RecipeRepository recipeRepository,
                             IngredientRepository ingredientRepository, RecipeIngredientRepository recipeIngredientRepository, VideoRepository videoRepository, IngredientService ingredientService, CategoryService categoryService, UnitService unitService, CategoryRepository categoryRepository,
                             Mapper mapper) {
        this.recipeRepository = recipeRepository;
        this.ingredientRepository = ingredientRepository;
        this.recipeIngredientRepository = recipeIngredientRepository;
        this.videoRepository = videoRepository;
        this.ingredientService = ingredientService;
        this.categoryService = categoryService;
        this.unitService = unitService;
        this.categoryRepository = categoryRepository;
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
        if (recipe.getIsDeleted()) {
            return null;
        }
        return mapper.toRecipeResponseDTO(recipe);
    }

    @Override
    public List<RecipeResponse> getRecipes() {
        List<Recipe> recipes = recipeRepository.findAll();
        List<RecipeResponse> recipeResponses = new ArrayList<>();

        for (Recipe recipe : recipes) {
            if (!recipe.getIsDeleted()) {
                recipeResponses.add(mapper.toRecipeResponseDTO(recipe));
            }
        }

        return recipeResponses;
    }

    @Override
    @Transactional
    public RecipeResponse updateRecipe(int id, RecipeRequest recipeRequest) {
        Recipe recipe = recipeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Recipe not found with id: " + id));

        Recipe recipeFromRequest = mapper.toRecipe(recipeRequest);
        recipe.setTitle(recipeRequest.getTitle());
        recipe.setShortDescription(recipeRequest.getShortDescription());
        recipe.setInstructions(recipeRequest.getSteps());


        Optional<Video> video = videoRepository.findFirstByUrl(recipeRequest.getVideoURL());
        if (video.isPresent()) {
            recipe.setVideo(video.get());
        } else {
            recipe.setVideo(recipeFromRequest.getVideo());
        }

        recipe.getIngredients().clear();

        for (var ingredient : recipeRequest.getIngredients()) {
            addIngredientsToRecipe(ingredient, recipe);
        }

        recipe.setDeleted(false);
        recipeRepository.save(recipe);

        return mapper.toRecipeResponseDTO(recipe);
    }

    @Override
    @Transactional
    public RecipeResponse save(RecipeRequest recipeRequest) {
        Recipe recipe = mapper.toRecipe(recipeRequest);

        Optional<Recipe> recipeFromDB = recipeRepository.findRecipeByTitle(recipeRequest.getTitle());
        if (recipeFromDB.isPresent()) {
            recipe.setId(recipeFromDB.get().getId());
        }

        recipeRepository.save(recipe);

        for (var ingredient : recipeRequest.getIngredients()) {

            addIngredientsToRecipe(ingredient, recipe);

        }

        recipeRepository.save(recipe);
        return mapper.toRecipeResponseDTO(recipe);
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
            if (!recipe.getIsDeleted()) {
                recipeResponses.add(mapper.toRecipeResponseDTO(recipe));
            }
        }

        return recipeResponses;
    }

    private void addIngredientsToRecipe(IngredientRequest ingredient, Recipe recipe) {

        //create and find an ingredient in the db
        Ingredient ingredientFromUser = new Ingredient(ingredient.getIngredientName());
        Ingredient ingFromDB = ingredientService.addIngredient(ingredientFromUser); //get ingr with id
        ingFromDB.setDeleted(false);

        //create and find an ingredient in the db
        Category categoryToFind = new Category(ingredient.getCategoryName());

        Optional<Category> categoryFromUser = categoryRepository.findFirstByCategoryName(categoryToFind.getCategoryName());
        Category category;

        if (categoryFromUser.isPresent()) {
            category = categoryFromUser.get();
        } else {
            category = new Category(categoryToFind.getCategoryName());
        }

        category.setDeleted(false);

        //set a category for a found ingredient
        ingFromDB.setCategory(category);

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

        Optional<RecipeIngredient> recipeIngredientIfExists = recipeIngredientRepository.findByRecipeAndIngredient(recipe, ingFromDB);

        RecipeIngredient recipeIngredient;

        if (recipeIngredientIfExists.isEmpty()) {
            recipeIngredient = new RecipeIngredient(recipe, ingFromDB,
                    amount, unit);
        } else {
            recipeIngredient = recipeIngredientIfExists.get();
        }

        recipe.addIngredient(recipeIngredient);
    }

}
