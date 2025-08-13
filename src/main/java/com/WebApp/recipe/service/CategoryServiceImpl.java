package com.WebApp.recipe.service;

import com.WebApp.recipe.entity.Category;
import com.WebApp.recipe.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    /*CRUD operations are created out of the box by spring boot rest
    POST: Creates a new resource (override, lines 35-41)
    GET: Reads/Retrieve a resource ("/categories", "/categories/{id}")
    PUT: Updates an existing resource ("/categories/{id}")
    DELETE: Deletes a resource ("/categories/{id}")
    */

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    /**
     * this method checks if the category exists. If not saves to db.
     * @param category to add
     * @return added or found category
     */
    @Override
    @Transactional
    public Category addCategory(Category category) {
        Optional<Category> foundCategory = categoryRepository.findById(category.getId());

        if (foundCategory.isEmpty()) {
            Category saved = categoryRepository.save(category);
            saved.lowerCaseName();
            return saved;
        }
        foundCategory.get().lowerCaseName();

        return foundCategory.get();
    }
}
