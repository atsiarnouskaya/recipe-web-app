package com.WebApp.recipe.service;

import com.WebApp.recipe.entity.Category;
import com.WebApp.recipe.entity.Ingredient;
import com.WebApp.recipe.repository.CategoryRepository;
import com.WebApp.recipe.repository.IngredientRepository;
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
        Optional<Ingredient> optional = ingredientRepository.findFirstByName(ingredient.getName());
        if (optional.isPresent()) {
            return optional.get();
        }
        return ingredientRepository.save(ingredient);
    }
}
