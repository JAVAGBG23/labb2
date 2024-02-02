package com.labb2.recipes_api.services;

import com.labb2.recipes_api.exception.EntityNotFoundException;
import com.labb2.recipes_api.models.Comment;
import com.labb2.recipes_api.models.Recipe;
import com.labb2.recipes_api.repository.RecipeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {
    @Autowired
    RecipeRepository recipeRepository;

    @Autowired
    private CommentService commentService;

    // skapa ett recept
    public Recipe addRecipe(Recipe recipe) {
        return recipeRepository.save(recipe);
    }

    // hämta alla recept
    public List<Recipe> getAllRecipes() {
        return recipeRepository.findAll();
    }

    // hämta specifikt recept med id
    public Optional<Recipe> getRecipeById(String id) {
        return recipeRepository.findById(id);
    }

    // funkar likadant
  /*  public Recipe getRecipeById(String id) {
        return recipeRepository.findById(id).get();
    }*/

   // uppdatera
   public Recipe updateRecipe(String id, Recipe updatedRecipe) {
       return recipeRepository.findById(id)
               .map(existingRecipe -> {
                   if (updatedRecipe.getTitle() != null) {
                       existingRecipe.setTitle(updatedRecipe.getTitle());
                   }
                   if (updatedRecipe.getDescription() != null) {
                       existingRecipe.setDescription(updatedRecipe.getDescription());
                   }
                   if (updatedRecipe.getIngredients() != null) {
                       existingRecipe.setIngredients(updatedRecipe.getIngredients());
                   }
                   if (updatedRecipe.getTags() != null) {
                       existingRecipe.setTags(updatedRecipe.getTags());
                   }
                   return recipeRepository.save(existingRecipe);
               })
               .orElseThrow(() -> new EntityNotFoundException("Recipe with id: " + id + " was not found!"));
   }

   // delete
    public void deleteRecipe(String id) {
       recipeRepository.deleteById(id);
    }

    // filtrera på taggar
    public List<Recipe> findRecipesByTags(List<String> tags) {
       return recipeRepository.findByTagsIn(tags);
    }

    // pagination och sortering
    // i spring data är Page en del av ramverkets stöd för pagination och representerar en
    // paginerad "sida" av data. Page är en subinterface till Slice och tillhandahåller
    // ytterligare information utöver datan, som totalt antal sidor och totalt antal element.
    // det är särskilt användbart när du behöver hantera stora mängder data på ett effektivt
    // sätt i din applikation.

    // en Page innehåller en lista över element på en specifik sida
    // den tillhandahåller också viktig pagination-information, som det totala antalet sidor,
    // det totala antalet element, antalet element på den aktuella sidan, och om det
    // finns fler sidor tillgängliga.
    // när du använder Page i samband med Spring Data-repositories, kan du utföra
    // databasfrågor som automatiskt tar hänsyn till pagination.
    // detta innebär att databasen endast hämtar de data som behövs för den
    // specifika sidan, vilket ökar effektiviteten särskilt för stora datamängder.
    public Page<Recipe> getRecipeWithPaginationAndSorting(int page, int size, String sortBy) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        // Pageable är ett interface som innehåller information om pagination och sortering.
        // det används för att berätta för ditt repository hur man ska paginera och
        // sortera resultaten.

        // PageRequest.of(page, size, Sort.by(sortBy)):
        // denna statiska metod skapar en Pageable-instans. page anger sidnumret
        // (börjar från 0), size anger antalet element per sida, och Sort.by(sortBy)

        return recipeRepository.findAll(pageable);
        //recipeRepository.findAll(pageable): denna metodanrop gör en fråga till databasen,
        // som hämtar enbart de data som behövs för den angivna sidan, och returnerar
        // resultaten som en Page<Recipe>. Page-objektet innehåller både
        // recepten för den aktuella sidan och pagination-informationen.

        // extra bra om vi har stora listor av recept.
    }

    // lägga till kommentar till recept med ObjectId referens
    public Recipe addCommentToRecipe(String recipeId, Comment comment) {
        Recipe recipe = recipeRepository.findById(recipeId).orElseThrow(() -> new RuntimeException("Recipe not found"));
        Comment savedComment = commentService.saveComment(comment);
        recipe.getComments().add(savedComment);
        return recipeRepository.save(recipe);
    }

    // lägga till kommentar till recept inbäddat dokument
    //public Recipe addCommentToRecipe(String recipeId, Comment comment) {
      /*  return recipeRepository.findById(recipeId)
                .map(recipe -> {
                    recipe.addComment(comment);
                    return recipeRepository.save(recipe);
                })
                .orElseThrow(() -> new EntityNotFoundException("Recipe with id: " + recipeId + " was not found!"));
    }*/

    // söka på ett recept baserat på ingredienser
    public List<Recipe> findRecipesByIngredients(List<String> ingredients) {
        return recipeRepository.findByIngredientsIn(ingredients);
    }
}
