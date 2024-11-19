package com.cg.casestudy.services.impl;

import com.cg.casestudy.dtos.PostDTO;
import com.cg.casestudy.models.post.Comment;
import com.cg.casestudy.models.post.Post;
import com.cg.casestudy.repositories.CommentRepository;
import com.cg.casestudy.repositories.PostRepository;
import com.cg.casestudy.services.PostService;
import com.cg.casestudy.utils.CommonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {
    private final PostRepository postRepository;

    private final CommentRepository commentRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, CommentRepository commentRepository) {
        this.postRepository = postRepository;
        this.commentRepository = commentRepository;
    }

    @Override
    public List<PostDTO> getAllPosts() {
        List<Post> posts = postRepository.findAll();
        List<PostDTO> postDTOs = new ArrayList<>();


        for (Post post : posts) {
            PostDTO postDTO = CommonMapper.mapPostToPostDTO(post);
            List<Comment> comments = commentRepository.findByPostId(post.getId());
            postDTO.setImage(post.getImage().getUrl());
            postDTO.setComments(comments);
            postDTOs.add(postDTO);
        }

        return postDTOs;
    }

    @Override
    public List<Post> getPostById(Long postId)
    {
        List<Post> posts = new ArrayList<>();
        posts.add(postRepository.findById(postId).orElse(null));
        return posts;
    }
}
