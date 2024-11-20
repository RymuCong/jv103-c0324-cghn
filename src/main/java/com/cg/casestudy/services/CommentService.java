package com.cg.casestudy.services;

import com.cg.casestudy.dtos.CommentDTO;

import java.util.List;

public interface CommentService {

    List<CommentDTO> getCommentsByPostId(Long postId);
}
