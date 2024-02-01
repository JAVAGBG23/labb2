package com.labb2.recipes_api.services;

import com.labb2.recipes_api.models.Recipe;
import com.labb2.recipes_api.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RecipeService {
    @Autowired
    RecipeRepository recipeRepository;

    // skapa ett recept
    public Recipe addRecipe(Recipe recipe) {
        return recipeRepository.save(recipe);
    }
}
