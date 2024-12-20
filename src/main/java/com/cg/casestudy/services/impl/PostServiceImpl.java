package com.cg.casestudy.services.impl;

import com.cg.casestudy.dtos.PostDTO;
import com.cg.casestudy.dtos.PostRequest;
import com.cg.casestudy.models.common.Image;
import com.cg.casestudy.models.post.Liked;
import com.cg.casestudy.models.post.Post;
import com.cg.casestudy.models.user.User;
import com.cg.casestudy.repositories.ImageRepository;
import com.cg.casestudy.repositories.PostRepository;
import com.cg.casestudy.repositories.UserRepository;
import com.cg.casestudy.services.FirebaseService;
import com.cg.casestudy.services.PostService;
import com.cg.casestudy.utils.CommonMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final FirebaseService firebaseService;
    private final ImageRepository imageRepository;
    private final UserRepository userRepository;

    @Autowired
    public PostServiceImpl(PostRepository postRepository, FirebaseService firebaseService,
                           ImageRepository imageRepository, UserRepository userRepository) {
        this.postRepository = postRepository;
        this.firebaseService = firebaseService;
        this.imageRepository = imageRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<PostDTO> getAllPosts() {
        List<Post> posts = postRepository.findAllOrderByCreatedAtDesc();
        List<PostDTO> postDTOs = new ArrayList<>();

        for (Post post : posts) {
            PostDTO postDTO = CommonMapper.mapPostToPostDTO(post);
            postDTOs.add(postDTO);
        }

        return postDTOs;
    }

    @Override
    public List<Post> getPostById(Long postId) {
        List<Post> posts = new ArrayList<>();
        posts.add(postRepository.findById(postId).orElse(null));
        return posts;
    }

    @Override
    public List<PostDTO> getPostsByUser(User createdBy) {
        List<Post> posts = postRepository.findByCreatedBy(createdBy);
        List<PostDTO> postDTOs = new ArrayList<>();

        for (Post post : posts) {
            PostDTO postDTO = CommonMapper.mapPostToPostDTO(post);
            postDTOs.add(postDTO);
        }

        return postDTOs;
    }

    @Override
    public void save(PostRequest post, MultipartFile file) {
        Post newPost = CommonMapper.mapPostRequestToPost(post);
        if (file != null) {
            Image savedImage = new Image();
            savedImage.setUrl(firebaseService.uploadImageToFireBase(file));
            savedImage.setUserImage(newPost.getCreatedBy());
            newPost.setImage(savedImage);
        } else {
            newPost.setImage(null);
        }

        postRepository.save(newPost);
    }

    @Transactional
    @Override
    public PostDTO likePost(Long postId, Long userId) {
        Post post = postRepository.findByIdWithLikes(postId).orElse(null);
        if (post == null) {
            return null;
        }

        User likedBy = userRepository.findById(userId).orElse(null);
        if (likedBy == null) {
            return null;
        }

        // Check if the user has already liked the post
        boolean alreadyLiked = post.getLikes().stream()
                .anyMatch(like -> like.getLikedBy().getId().equals(userId));

        if (alreadyLiked) {
            return CommonMapper.mapPostToPostDTO(post);
        }

        Liked newLike = Liked.builder().post(post).likedBy(likedBy).build();
        post.getLikes().add(newLike);
        postRepository.save(post);

        return CommonMapper.mapPostToPostDTO(post);
    }

}