package com.WebApp.recipe.Services.RecipeSevices;

import com.WebApp.recipe.Entities.RecipeEntities.Ingredient;
import com.WebApp.recipe.Repositories.RecipeRepositories.CategoryRepository;
import com.WebApp.recipe.Repositories.RecipeRepositories.IngredientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class IngredientServiceImpl implements IngredientService{

     /*CRUD operations are created out of the box by spring boot rest
    POST: Creates a new resource (overridden, "custom/ingredients")
    GET: Reads/Retrieve a resource ("/ingredients", "/ingredients/{id}")
    PUT: Updates an existing resource ("/ingredients/{id}")
    DELETE: Deletes a resource ("/ingredients/{id}")
    */

    private final IngredientRepository ingredientRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public IngredientServiceImpl(IngredientRepository ingredientRepository, CategoryRepository categoryRepository) {
        this.ingredientRepository = ingredientRepository;
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional
    public Ingredient addIngredient(Ingredient ingredient) {

        Optional<Ingredient> foundIngredient = ingredientRepository.findFirstByName(ingredient.getName());

        if (foundIngredient.isPresent()) {
            foundIngredient.get().setDeleted(false);
            return foundIngredient.get();
        }

        return ingredientRepository.save(ingredient);
    }
}
