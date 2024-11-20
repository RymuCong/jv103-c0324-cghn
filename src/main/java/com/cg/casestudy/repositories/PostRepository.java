package com.cg.casestudy.repositories;

import com.cg.casestudy.models.post.Post;
import com.cg.casestudy.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {

    @Query("SELECT p FROM Post p ORDER BY p.createdAt DESC")
    List<Post> findAllOrderByCreatedAtDesc();

    @Query("SELECT p FROM Post p WHERE p.createdBy = :createdBy ORDER BY p.createdAt DESC")
    List<Post> findByCreatedBy(@Param("createdBy") User createdBy);

}
