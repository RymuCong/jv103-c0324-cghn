package com.cg.casestudy.repositories;

import com.cg.casestudy.models.post.Post;
import com.cg.casestudy.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByCreatedBy(User user);
}
