package com.cg.casestudy.repositories;

import com.cg.casestudy.models.post.Post;
import com.cg.casestudy.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("SELECT p FROM Post p ORDER BY p.createdAt DESC")
    List<Post> findAllOrderByCreatedAtDesc();

    @Query("SELECT p FROM Post p WHERE p.createdBy = :createdBy ORDER BY p.createdAt DESC")
    List<Post> findByCreatedBy(@Param("createdBy") User createdBy);

    @Query("SELECT p FROM Post p LEFT JOIN FETCH p.likes WHERE p.id = :postId")
    Optional<Post> findByIdWithLikes(Long postId);
}
