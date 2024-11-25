package com.cg.casestudy.services;

import com.cg.casestudy.dtos.CommentDTO;

import java.util.List;

public interface CommentService {

    List<CommentDTO> getCommentsByPostId(Long postId);

    CommentDTO addComment(Long postId, CommentDTO data, Long userId);

    CommentDTO updateComment(Long id, CommentDTO data);

}
