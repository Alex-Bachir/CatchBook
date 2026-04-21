package com.catchbook.catchservice.service;

import com.catchbook.catchservice.model.Comment;
import com.catchbook.catchservice.repository.CommentRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    public CommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    public Comment getCommentById(Long id) {
        return commentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
    }


    public List<Comment> getCommentsByCatchId(Long catchId) {
        return commentRepository.findByFishCatch_CatchId(catchId);
    }

    public Comment saveComment(Comment comment) {
        return commentRepository.save(comment);
    }

    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }




}
