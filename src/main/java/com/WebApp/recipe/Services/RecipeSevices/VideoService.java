package com.WebApp.recipe.Services.RecipeSevices;

import com.WebApp.recipe.Entities.RecipeEntities.Video;

public interface VideoService {
    Video findByURL(String url);
}
