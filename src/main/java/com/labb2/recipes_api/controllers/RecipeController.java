package com.labb2.recipes_api.controllers;

import com.labb2.recipes_api.exception.EntityNotFoundException;
import com.labb2.recipes_api.models.Comment;
import com.labb2.recipes_api.models.Recipe;
import com.labb2.recipes_api.services.RecipeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

    // PUT
    @PutMapping("/{id}")
    public ResponseEntity<?> updateRecipe(@PathVariable String id, @Valid @RequestBody Recipe recipeDetails) {
        try {
              Recipe updatedRecipe = recipeService.updateRecipe(id, recipeDetails);
              return ResponseEntity.ok(updatedRecipe);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteRecipe(@PathVariable String id) {
        recipeService.deleteRecipe(id);
        return ResponseEntity.ok("Recipe with id: " + id + " has been deleted!");
    }

    // GET filtrera på taggar
    @GetMapping("/search")
    public List<Recipe> findRecipesByTags(@RequestParam List<String> tags) {
        return recipeService.findRecipesByTags(tags);
    }


   // pagination & sorting
   @GetMapping
   public Page<Recipe> getRecipeWithPaginationAndSorting(
           @RequestParam(defaultValue = "0") int page,
           @RequestParam(defaultValue = "10") int size,
           @RequestParam(defaultValue = "id") String sortBy) {
        return recipeService.getRecipeWithPaginationAndSorting(page, size, sortBy);
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

















