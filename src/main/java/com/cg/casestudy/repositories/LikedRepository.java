package com.cg.casestudy.repositories;

import com.cg.casestudy.models.post.Liked;
import com.cg.casestudy.models.post.Post;
import com.cg.casestudy.models.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikedRepository extends JpaRepository<Liked, Long> {

    List<Liked> findByPostId(Long postId);

    Liked findByPostAndLikedBy(Post post, User user);

    //    void deleteByPostAndLikedBy(Post post, User user);
}
