package com.labb2.recipes_api.models;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "comments")
public class Comment {
    @Id
    private String id;
    //id behövs ej så här om vi gör inbäddat

    private String author = "Helena";

    private String text;

    @CreatedDate
    private Date created_at;
    //behövs ej så här om vi gör inbäddat

    public Comment() {
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public Date getCreated_at() {
        return created_at;
    }
}












