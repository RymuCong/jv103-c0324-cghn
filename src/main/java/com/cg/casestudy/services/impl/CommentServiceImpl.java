package com.cg.casestudy.services.impl;

import com.cg.casestudy.dtos.CommentDTO;
import com.cg.casestudy.models.post.Comment;
import com.cg.casestudy.repositories.CommentRepository;
import com.cg.casestudy.services.CommentService;
import com.cg.casestudy.utils.CommonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    @Autowired
    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
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
}
