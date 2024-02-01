package com.labb2.recipes_api.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;

@Document(collection = "recipes")
public class Recipe {
    @Id
    private String id;

    @NotBlank(message = "Field cannot be blank")
    private String title;

    // @NotBlank kan användas på String typ
    @NotBlank(message = "Field cannot be blank")
    private String description;

    // @NotEmpty = collections, hashmap, arrays etc
    @NotEmpty(message = "Field cannot be blank")
    private List<String> ingredients;

    private List<String> tags = new ArrayList<>();

    // inbäddade kommentarer i dokumentet
    //private List<Comment> comments = new ArrayList<>();

    // referens med ObjectId
    @DBRef
    private List<Comment> comments = new ArrayList<>();

    public Recipe() {
    }


    // metod för att lägga till en kommentar
    public void addComment(Comment comment) {
        this.comments.add(comment);
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<String> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getId() {
        return id;
    }

    public List<Comment> getComments() {
        return comments;
    }

    public void setComments(List<Comment> comments) {
        this.comments = comments;
    }
}
