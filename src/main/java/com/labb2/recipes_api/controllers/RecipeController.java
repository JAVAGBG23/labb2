package com.labb2.recipes_api.controllers;

import com.labb2.recipes_api.models.Comment;
import com.labb2.recipes_api.models.Recipe;
import com.labb2.recipes_api.services.RecipeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/recipes")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    // POST
    @PostMapping
    public ResponseEntity<Recipe> addRecipe(@Valid @RequestBody Recipe recipe) {
        Recipe newRecipe = recipeService.addRecipe(recipe);
        return new ResponseEntity<>(newRecipe, HttpStatus.CREATED);
    }

     // GET all
    @GetMapping("/all")
    public List<Recipe> getAllRecipes() {
        return recipeService.getAllRecipes();
    }

    // GET by id
    @GetMapping("/{id}")
    public ResponseEntity<Recipe> getRecipeById(@PathVariable String id) {
        Optional<Recipe> recipe = recipeService.getRecipeById(id);

        return recipe.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }












    // POST lägga till kommentar till recept med ObjectId referens
    @PostMapping("/{recipeId}/comments")
    public ResponseEntity<Recipe> addCommentToRecipe(@PathVariable String recipeId, @RequestBody Comment comment) {
           Recipe updatedRecipe = recipeService.addCommentToRecipe(recipeId, comment);
           return ResponseEntity.ok(updatedRecipe);
    }




    // POST lägga till kommentar till recept med inbäddat dokument
  /*  @PostMapping("/{recipeId}/comments")
    public ResponseEntity<Recipe> addCommentToRecipe(@PathVariable String recipeId, @RequestBody Comment comment) {
        try {
             Recipe updatedRecipe = recipeService.addCommentToRecipe(recipeId, comment);
             return ResponseEntity.ok(updatedRecipe);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }*/














}

















