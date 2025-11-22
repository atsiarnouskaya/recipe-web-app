package com.WebApp.recipe.controller;

import com.WebApp.recipe.Security.DTOs.UserResponse;
import com.WebApp.recipe.Security.entity.User;
import com.WebApp.recipe.Security.service.UserService;
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
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/custom")
public class RecipeController {

    private final RecipeService recipeService;
    private final UserService userService;

    @Autowired
    public RecipeController(RecipeService recipeService, UserService userService) {
        this.recipeService = recipeService;
        this.userService = userService;
    }

    @PostMapping("/addRecipe")
    public RecipeResponse addRecipe(@RequestBody RecipeRequest recipeRequest,
                                    @AuthenticationPrincipal UserDetails userDetails) {

        String username = userDetails.getUsername();
        recipeRequest.setUsername(username);
        return recipeService.save(recipeRequest);
    }

    @GetMapping("/recipe/{id}")
    public RecipeResponse getRecipe(@PathVariable int id) {
        return recipeService.getRecipeById(id);
    }

    @GetMapping("/recipes")
    public List<RecipeResponse> getAllRecipes(HttpSession session) {
        return recipeService.getRecipes();
    }

    @GetMapping("/{userId}/favRecipes")
    public List<RecipeResponse> getFavRecipes(@PathVariable int userId) {
        return recipeService.userFavRecipes(userId);
    }

    @PutMapping("/fav")
    public RecipeResponse favRecipe(@RequestBody int recipeId,
                                    @AuthenticationPrincipal UserDetails userDetails) {
        UserResponse user = userService.getUserByUsername(userDetails.getUsername());
        if (user == null) {
            throw new RuntimeException("User not found");
        }
        RecipeResponse favedRecipe = recipeService.likeRecipe(recipeId, user.getId());
        return favedRecipe;
    }

    @PutMapping("/recipes/{id}")
    public RecipeResponse updateRecipe(@PathVariable int id, @RequestBody RecipeRequest recipeRequest) {
        return recipeService.updateRecipe(id, recipeRequest);
    }

    @GetMapping("/getRecipesByIngredients")
    public List<RecipeResponse> getAllRecipesByIngredients(@RequestBody List<IngredientRequest> ingredients) {
        return recipeService.getRecipesByIngredients(ingredients);
    }

    @GetMapping("/recipes/{userId}")
    public List<RecipeResponse>  getRecipesByUserId(@PathVariable int userId) {
        return recipeService.getRecipesByAuthor(userId);
    }

    @PutMapping("deleteRecipe/{id}")
    public RecipeResponse deleteRecipe(@PathVariable int id) {
        return recipeService.deleteRecipe(id);
    }

}
