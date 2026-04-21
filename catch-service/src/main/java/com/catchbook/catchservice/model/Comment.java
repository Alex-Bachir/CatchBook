package com.catchbook.catchservice.model;

import jakarta.persistence.*;
import lombok.Data;
import java.time.LocalDateTime;

@Entity
@Data
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long commentId;

    @ManyToOne
    @JoinColumn(name = "catch_id")
    private Catch fishCatch;  // plusieurs commentaires pour 1 prise

    @Column(name = "user_id")
    private Long userId;

    private String content;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}