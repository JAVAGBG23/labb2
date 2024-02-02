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
        //att använda ResponseEntity i spring boot är valfritt, men ofta föredragen praxis när man utvecklar REST API:er.
        //den erbjuder flera fördelar jämfört med att bara returnera modellklassen direkt i controllermetoderna.
        //ResponseEntity ger dig fullständig kontroll över HTTP-statuskoden, headers och body när du ska skicka tillbaka svaret.
        // du kan också använda spring boots exception handling-mekanismer för att fånga upp fel och returnera ett lämpligt ResponseEntity-objekt.
        // det ökar även läsbarheten av din kod och du följer standard praxis.
        Recipe newRecipe = recipeService.addRecipe(recipe);
        return new ResponseEntity<>(newRecipe, HttpStatus.CREATED);
    }

     // GET all
     // i vissa fall kan ResponseEntity vara lite överflödigt som i fallet att hämta alla recept
    @GetMapping("/all")
    public List<Recipe> getAllRecipes() {
        return recipeService.getAllRecipes();
    }

    // GET by id
    @GetMapping("/{id}")
    public ResponseEntity<Recipe> getRecipeById(@PathVariable String id) {
        Optional<Recipe> recipe = recipeService.getRecipeById(id);
        //optional är en containerklass introducerad i Java 8 som kan innehålla noll eller ett värde.
        //dess huvudsakliga syfte är att ge en mer elegant lösning för att hantera null-värden, vilket
        // minskar risken för NullPointerException.
        // i denna metod används Optional för att hantera situationen där ett recept med det angivna ID:t
        // kanske inte finns. recipeService.getRecipeById(id) returnerar ett Optional<Recipe>, vilket
        // betyder att det antingen innehåller ett Recipe-objekt (om ett sådant finns) eller
        // är tomt (om inget recept med det ID:t hittades).

        //metoden använder map- och orElseGet-metoderna från Optional-klassen
        // för att hantera de två möjliga utfallen

        //receptet Hittades (Optional är full):
        return recipe.map(ResponseEntity::ok)
                //om Optional innehåller ett Recipe-objekt, använder map-metoden det värdet
                // för att skapa en ResponseEntity med statuskoden 200 OK.
                // ResponseEntity::ok är en metodreferens som skapar en ResponseEntity med
                // innehållet i Optional.

                //receptet Hittades Inte (Optional är tomt):
                .orElseGet(() -> ResponseEntity.notFound().build());
        //om Optional är tomt (inget recept hittades), använder orElseGet en
        //lambda-uttryck för att skapa
        //en ResponseEntity med statuskoden 404 Not Found.
    }

    //ResponseEntity<?> och ResponseEntity<String> är två olika sätt att specificera returtypen
    // för en metod i Spring Framework, vanligtvis i en controller-klass.
    // De är varianter av ResponseEntity, som är en behållare för hela HTTP-svaret inklusive statuskod,
    // headers och kropp. Här är skillnaden mellan de två:

    //ResponseEntity<?>: Användningen av ett frågetecken (?), som kallas ett wildcard, gör denna typ generisk.
    // Det innebär att metoden kan returnera en ResponseEntity med vilken typ av kropp som helst.

    //ResponseEntity<String>: Här specificeras att metoden alltid kommer att returnera en
    // ResponseEntity med en sträng som kropp. Det ger klarhet om vad som förväntas returneras
    // och kan vara mer informativt för andra utvecklare som läser koden.
    // Det kan också vara fördelaktigt för typsäkerhet, eftersom kompilatorn kan kontrollera
    // att metoden faktiskt returnerar en sträng.

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

















