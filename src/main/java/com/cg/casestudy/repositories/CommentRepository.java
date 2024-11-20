package com.cg.casestudy.repositories;

import com.cg.casestudy.models.post.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findCommentsByPostId(Long postId);

}
