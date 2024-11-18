package com.cg.casestudy.services;

import com.cg.casestudy.dtos.PostDTO;
import com.cg.casestudy.models.post.Post;

import java.util.List;

public interface PostService {

    List<PostDTO> getAllPosts();

    List<Post> getPostById(Long postId);
}
