package com.WebApp.recipe.service;

import com.WebApp.recipe.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecipeServiceImpl {

    private final RecipeRepository recipeRepository;

    @Autowired
    public RecipeServiceImpl(RecipeRepository recipeRepository) {
        this.recipeRepository = recipeRepository;
    }

      /*CRUD operations are created out of the box by spring boot rest
    POST: Creates a new resource ("/recipes")
    GET: Reads/Retrieve a resource ("/recipes", "/recipes/{id}")
    PUT: Updates an existing resource ("/recipes/{id}")
    DELETE: Deletes a resource ("/recipes/{id}")
    */
}
