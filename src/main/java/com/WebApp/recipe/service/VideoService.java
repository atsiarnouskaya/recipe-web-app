package com.WebApp.recipe.service;

import com.WebApp.recipe.entity.Video;

public interface VideoService {
    Video findByURL(String url);
}
