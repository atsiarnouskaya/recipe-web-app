package com.WebApp.recipe.controller;

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

@RestController
@RequestMapping("/custom")
public class RecipeController {

    private final RecipeService recipeService;

    @Autowired
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    /**
     *
     * @param recipeRequest
     * @param userDetails
     * @return ResponseEntity with status 200 if no error occurred while saving recipe
     */
    @PostMapping("/addRecipe")
    public ResponseEntity<RecipeResponse> addRecipe(@Valid @RequestBody RecipeRequest recipeRequest,
                                                    @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        recipeRequest.setUsername(username);

        RecipeResponse recipeResponse = recipeService.save(recipeRequest);

        if (recipeResponse != null) {
            return ResponseEntity.ok(recipeResponse);
        }

        return ResponseEntity.badRequest().build(); //null can be returned if there is no such user in a db
    }

    /**
     *
     * @param id
     * @return ResponseEntity with status 200 if recipe with given id was found
     */
    @GetMapping("/recipe/{id}")
    public ResponseEntity<RecipeResponse> getRecipe(@PathVariable int id) {
        RecipeResponse recipeResponse = recipeService.getRecipeById(id);

        if (recipeResponse != null) {
            return ResponseEntity.ok(recipeResponse);
        }

        return ResponseEntity.status(204).build();
    }

    /**
     * @return List of all saved recipes or not found status if no recipes were found
     */
    @GetMapping("/recipes")
    public ResponseEntity<List<RecipeResponse>> getAllRecipes() {
        List<RecipeResponse> recipes = recipeService.getRecipes();

        if (recipes.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.ok(recipes);
    }

    /**
     *
     * @param userId
     * @return List of users favourite recipes
     */
    @GetMapping("/{userId}/favRecipes")
    public ResponseEntity<List<RecipeResponse>>  getFavRecipes(@PathVariable int userId) {
        List<RecipeResponse> recipes = recipeService.userFavRecipes(userId);

        if (recipes.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.ok(recipes);
    }


    /**
     *
     * @param favRequest
     * @param userDetails
     * @return If recipe wasn't liked before -- liking recipe occurs. Otherwise -- disliking. Based on recipe id from favRequest and username from userDetails
     */
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

    /**
     * @param id
     * @param recipeRequest
     * @param userDetails
     * @return If user has rights to change this recipe -- status 200 and updated recipe is returned. Otherwise -- 403
     */
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
    public ResponseEntity<List<RecipeResponse>>   getAllRecipesByIngredients(@RequestBody List<IngredientRequest> ingredients) {
        List<RecipeResponse> recipes = recipeService.getRecipesByIngredients(ingredients);

        if (recipes.isEmpty()) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.ok(recipes);
    }

    /**
     *
     * @param userId
     * @return If user has some added recipes, list with those recipes is returned, otherwise - 404
     */
    @GetMapping("/recipes/{userId}")
    public ResponseEntity<List<RecipeResponse>>  getRecipesByUserId(@PathVariable int userId) {
        List<RecipeResponse> recipes = recipeService.getRecipesByAuthor(userId);

        if (recipes == null) {
            return ResponseEntity.status(204).build();
        }

        return ResponseEntity.ok(recipes);
    }

    /**
     * @param id
     * @param userDetails
     * @return If user has rights to delete this recipe -- status 200 and deleting recipe (in db flag isDeleted) is returned. Otherwise -- 403
     */
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
