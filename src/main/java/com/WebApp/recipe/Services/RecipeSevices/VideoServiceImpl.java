package com.WebApp.recipe.Services.RecipeSevices;

import com.WebApp.recipe.Entities.RecipeEntities.Video;
import com.WebApp.recipe.Repositories.RecipeRepositories.VideoRepository;

public class VideoServiceImpl implements VideoService {

    private final VideoRepository videoRepository;

    public VideoServiceImpl(VideoRepository videoRepository) {
        this.videoRepository = videoRepository;
    }

    @Override
    public Video findByURL(String url) {
        return null;
    }
}
