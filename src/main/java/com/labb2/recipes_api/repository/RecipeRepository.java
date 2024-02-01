package com.labb2.recipes_api.repository;

import com.labb2.recipes_api.models.Recipe;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface RecipeRepository extends MongoRepository<Recipe, String> {
}
