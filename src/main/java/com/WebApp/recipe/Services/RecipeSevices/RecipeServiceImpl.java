package com.WebApp.recipe.Services.RecipeSevices;

import com.WebApp.recipe.Entities.RecipeEntities.*;
import com.WebApp.recipe.Repositories.RecipeRepositories.*;
import com.WebApp.recipe.Entities.UserEntities.User;
import com.WebApp.recipe.Repositories.UserRepositories.UserRepository;
import com.WebApp.recipe.Services.UserSevices.UserService;
import com.WebApp.recipe.DTOs.IngredientDTOs.IngredientRequest;
import com.WebApp.recipe.DTOs.Mapper;
import com.WebApp.recipe.DTOs.RecipeDTOs.RecipeRequest;
import com.WebApp.recipe.DTOs.RecipeDTOs.RecipeResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
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
    private final UserService userService;
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final Mapper mapper;

    @Autowired
    public RecipeServiceImpl(RecipeRepository recipeRepository,
                             IngredientRepository ingredientRepository, RecipeIngredientRepository recipeIngredientRepository, VideoRepository videoRepository, IngredientService ingredientService, CategoryService categoryService, UnitService unitService, UserService userService, CategoryRepository categoryRepository, UserRepository userRepository,
                             Mapper mapper) {
        this.recipeRepository = recipeRepository;
        this.ingredientRepository = ingredientRepository;
        this.recipeIngredientRepository = recipeIngredientRepository;
        this.videoRepository = videoRepository;
        this.ingredientService = ingredientService;
        this.categoryService = categoryService;
        this.unitService = unitService;
        this.userService = userService;
        this.categoryRepository = categoryRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

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
                RecipeResponse recipeResponse = mapper.toRecipeResponseDTO(recipe);
                recipeResponses.add(recipeResponse);
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
        recipe = recipeRepository.save(recipe);

        return mapper.toRecipeResponseDTO(recipe);
    }

    @Override
    @Transactional
    public RecipeResponse save(RecipeRequest recipeRequest) {
        Recipe recipe = mapper.toRecipe(recipeRequest);
        String username = recipeRequest.getUsername();
        Optional<Recipe> recipeFromDB = recipeRepository.findRecipeByTitle(recipeRequest.getTitle());

        if (recipeFromDB.isPresent()) {
            recipe = recipeFromDB.get();
            recipe.setDeleted(false);
        }
        else {
            Optional<User> user = userRepository.findUserByUsername(username);
            if (user.isPresent()) {
                recipe.setUser(user.get());
            } else {
                return null;
            }
            recipe.setDeleted(false);
            recipeRepository.save(recipe);
        }

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


    @Override
    @Transactional
    public RecipeResponse likeRecipe(int recipeId, String username) {
        Optional<User> userOpt = userRepository.findUserByUsername(username);
        Optional<Recipe> recipeOpt = recipeRepository.findById(recipeId);
        if (userOpt.isEmpty() || recipeOpt.isEmpty()) {
            throw new IllegalArgumentException("User or Recipe not found");
        }
        Recipe recipe = recipeOpt.get();
        recipe.addUsersLike(userOpt.get());
        recipeRepository.save(recipe);
        return mapper.toRecipeResponseDTO(recipe);
    }

    @Override
    @Transactional
    public RecipeResponse dislikeRecipe(int recipeId, String username) {
        Optional<User> userOpt = userRepository.findUserByUsername(username);
        Optional<Recipe> recipeOpt = recipeRepository.findById(recipeId);
        if (userOpt.isEmpty() || recipeOpt.isEmpty()) {
            throw new IllegalArgumentException("User or Recipe not found");
        }
        Recipe recipe = recipeOpt.get();
        recipe.deleteUsersLike(userOpt.get());
        recipeRepository.save(recipe);
        return mapper.toRecipeResponseDTO(recipe);
    }

    @Override
    @Transactional
    public RecipeResponse deleteRecipe(int id, String username) {
        Optional<Recipe> recipeFromDB = recipeRepository.findById(id);

        if (recipeFromDB.isPresent()) {
            if (!username.equals(recipeFromDB.get().getUser().getUsername())) {
                throw new AccessDeniedException("You are not allowed to delete this recipe");
            }
            recipeFromDB.get().deleteRecipe();
        }
        else {
            throw new RuntimeException("Recipe not found with id: " + id);
        }
        return mapper.toRecipeResponseDTO(recipeRepository.save(recipeFromDB.get()));
    }

    @Override
    public List<RecipeResponse> getRecipesByAuthor(int userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if (userOptional.isEmpty()) {
            throw new RuntimeException("User not found with id: " + userId);
        }
        User user = userOptional.get();
        List<Recipe> recipesByUser = recipeRepository.findRecipesByUser(user);
        List<RecipeResponse> recipeResponses = new ArrayList<>();

        if (!recipesByUser.isEmpty()) {
            for (Recipe recipe : recipesByUser) {
                if(!recipe.getIsDeleted()) {
                    recipeResponses.add(mapper.toRecipeResponseDTO(recipe));
                }
            }
            return recipeResponses;
        }
        return null;
    }

    @Override
    public List<RecipeResponse> userFavRecipes(int userId) {
        List<Recipe> recipes = userRepository.findRecipesByUserId(userId);
        List<RecipeResponse> recipeResponses = new ArrayList<>();
        if (!recipes.isEmpty()) {
            for (Recipe recipe : recipes) {
                if (recipe.getIsDeleted()) continue;
                recipeResponses.add(mapper.toRecipeResponseDTO(recipe));
            }
        }
        return recipeResponses;
    }

    private void addIngredientsToRecipe(IngredientRequest ingredient, Recipe recipe) {

        //create or find an ingredient in the db
        Optional<Ingredient> ingFromDB = ingredientRepository.findFirstByName(ingredient.getIngredientName());

        Ingredient ing;
        if (ingFromDB.isPresent()) {
            ing = ingFromDB.get();
        }
        else {
            ing = new Ingredient(ingredient.getIngredientName());
            ingredientRepository.save(ing);
        }
        ing.setDeleted(false);

        Optional<Category> categoryFromUser = categoryRepository.findFirstByCategoryName(ingredient.getCategoryName());

        Category category;
        if (categoryFromUser.isPresent()) {
            category = categoryFromUser.get();
        } else {
            category = new Category(ingredient.getCategoryName());
            categoryRepository.save(category);
        }
        category.setDeleted(false);

        //set a category for a found or created ingredient
        ing.setCategory(category);

        Unit unit = new Unit (ingredient.getUnit());

        Double amount = ingredient.getAmount();
        if (amount < 0) {
            amount = 0.0;
        }

        Unit unitFromDB = unitService.findByNameElseAdd(unit);


        Optional<Recipe> recipeFromDbOpt = recipeRepository.findById(recipe.getId());
        if (recipeFromDbOpt.isPresent()) {
            recipe = recipeFromDbOpt.get();
        } else {
            throw new RuntimeException("Recipe not found with id: " + recipe.getId());
        }

        Optional<RecipeIngredient> recipeIngredientIfExists = recipeIngredientRepository.findByRecipeAndIngredient(recipe, ing);

        RecipeIngredient recipeIngredient;
        if (recipeIngredientIfExists.isEmpty()) {
            recipeIngredient = new RecipeIngredient(recipe, ing,
                    amount, unitFromDB);
        } else {
            recipeIngredient = recipeIngredientIfExists.get();
        }

        recipe.addIngredient(recipeIngredient);
    }

}
