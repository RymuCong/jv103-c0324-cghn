package com.cg.casestudy.services.impl;

import com.cg.casestudy.dtos.CommentDTO;
import com.cg.casestudy.models.post.Comment;
import com.cg.casestudy.models.post.Post;
import com.cg.casestudy.repositories.CommentRepository;
import com.cg.casestudy.repositories.PostRepository;
import com.cg.casestudy.repositories.UserRepository;
import com.cg.casestudy.services.CommentService;
import com.cg.casestudy.utils.CommonMapper;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Override
    public List<CommentDTO> getCommentsByPostId(Long postId) {
        List<Comment> comments = commentRepository.findCommentsByPostId(postId);
        List<CommentDTO> commentDTOS = new ArrayList<>();
        for(Comment comment : comments){
            CommentDTO commentDTO = CommonMapper.mapCommentToCommentDTO(comment);
            commentDTOS.add(commentDTO);
        }
        return commentDTOS;
    }

    @Transactional
    @Override
    public CommentDTO addComment(Long postId, CommentDTO data, Long userId) {
        Post post = postRepository.findById(postId).orElse(null);
        if(post == null){
            return null;
        }
        Comment comment = new Comment();
        comment.setContent(data.getContent());
        comment.setPost(post);
        comment.setCommentedBy(userRepository.findById(userId).orElse(null));
        comment.setCreatedAt(LocalDateTime.now());
        commentRepository.save(comment);
        return CommonMapper.mapCommentToCommentDTO(comment);
    }

    @Override
    public CommentDTO updateComment(Long id, CommentDTO data) {
        return null;
    }
}
