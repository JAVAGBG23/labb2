package com.labb2.recipes_api.repository;

import com.labb2.recipes_api.models.Comment;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface CommentRepository extends MongoRepository<Comment, String> {
}
