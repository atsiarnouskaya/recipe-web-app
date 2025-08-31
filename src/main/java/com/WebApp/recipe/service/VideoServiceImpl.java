package com.WebApp.recipe.service;

import com.WebApp.recipe.entity.Video;
import com.WebApp.recipe.repository.VideoRepository;

import java.util.Optional;

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
