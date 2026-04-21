package com.catchbook.catchservice.controller;

import com.catchbook.catchservice.model.Comment;
import com.catchbook.catchservice.service.CommentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentController {

    private final CommentService commentService;

    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @GetMapping("/{id}")
    public Comment getCommentById(@PathVariable Long id) {
        return commentService.getCommentById(id);
    }

    @GetMapping("/catch/{catchId}")
    public List<Comment> getCommentsByCatchId(@PathVariable Long catchId) {
        return commentService.getCommentsByCatchId(catchId);
    }

    @PostMapping
    public Comment saveComment(@RequestBody Comment comment) {
        return commentService.saveComment(comment);
    }

    @DeleteMapping("/{id}")
    public void deleteCommentById(@PathVariable Long id) {
        commentService.deleteComment(id);
    }



}
