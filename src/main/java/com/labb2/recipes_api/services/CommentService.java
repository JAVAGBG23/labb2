package com.labb2.recipes_api.services;

import com.labb2.recipes_api.models.Comment;
import com.labb2.recipes_api.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    // spara kommentar i db
    public Comment saveComment(Comment comment) {
        return commentRepository.save(comment);
    }
}
