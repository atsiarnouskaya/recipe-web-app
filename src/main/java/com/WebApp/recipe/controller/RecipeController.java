package com.WebApp.recipe.controller;

import com.WebApp.recipe.Security.DTOs.UserResponse;
import com.WebApp.recipe.Security.service.UserService;
import com.WebApp.recipe.dto.IngredientDTOs.IngredientRequest;
import com.WebApp.recipe.dto.RecipeDTOs.FavRequest;
import com.WebApp.recipe.dto.RecipeDTOs.RecipeRequest;
import com.WebApp.recipe.dto.RecipeDTOs.RecipeResponse;
import com.WebApp.recipe.service.RecipeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public RecipeResponse addRecipe(@Valid @RequestBody RecipeRequest recipeRequest,
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
    public List<RecipeResponse> getAllRecipes() {
        return recipeService.getRecipes();
    }

    @GetMapping("/{userId}/favRecipes")
    public List<RecipeResponse> getFavRecipes(@PathVariable int userId) {
        return recipeService.userFavRecipes(userId);
    }

    @PutMapping("/fav")
    public ResponseEntity<RecipeResponse> favRecipe(@RequestBody FavRequest favRequest,
                                                    @AuthenticationPrincipal UserDetails userDetails) {
        RecipeResponse favedRecipe;
        try {
            if (!favRequest.isLiked()) {
                favedRecipe = recipeService.likeRecipe(favRequest.getRecipeId(), userDetails.getUsername());
            } else {
                favedRecipe = recipeService.dislikeRecipe(favRequest.getRecipeId(), userDetails.getUsername());
            }

        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(favedRecipe);
    }

    @PutMapping("/recipes/{id}")
    public ResponseEntity<RecipeResponse> updateRecipe(@PathVariable int id,
                                                       @Valid @RequestBody RecipeRequest recipeRequest,
                                                       @AuthenticationPrincipal UserDetails userDetails) {
        if (!userDetails.getUsername().equals(recipeRequest.getUsername())) {
            return ResponseEntity.status(403).build();
        }
        return ResponseEntity.ok(recipeService.updateRecipe(id, recipeRequest));
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
    public ResponseEntity<RecipeResponse> deleteRecipe(@PathVariable int id,
                                                       @AuthenticationPrincipal UserDetails userDetails) {
        RecipeResponse rp;
        try {
            rp = recipeService.deleteRecipe(id, userDetails.getUsername());
        } catch (AccessDeniedException e) {
            return ResponseEntity.status(403).build();
        }
        return ResponseEntity.ok(rp);
    }

}
