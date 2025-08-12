package com.WebApp.recipe.repository;

import com.WebApp.recipe.entity.Video;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path="videos")
public interface VideoRepository extends JpaRepository<Video, Integer> {
}
