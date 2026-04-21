package com.catchbook.catchservice.repository;

import com.catchbook.catchservice.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findByFishCatch_CatchId(Long catchId);
}
