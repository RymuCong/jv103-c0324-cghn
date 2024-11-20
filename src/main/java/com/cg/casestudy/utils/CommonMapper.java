package com.cg.casestudy.utils;

import com.cg.casestudy.dtos.CommentDTO;
import com.cg.casestudy.dtos.PostDTO;
import com.cg.casestudy.dtos.UserInfoDTO;
import com.cg.casestudy.models.post.Comment;
import com.cg.casestudy.models.post.Post;
import com.cg.casestudy.models.user.UserInfo;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

public class CommonMapper {


    // map Post to PostDTO
    static public PostDTO mapPostToPostDTO(Post post) {
        PostDTO postDTO = new PostDTO();
        BeanUtils.copyProperties(post, postDTO);
        postDTO.setImage(post.getImage() != null ? post.getImage().getUrl() : null);
        postDTO.setLikes(post.getLikes());
        List<CommentDTO> commentDTOs = new ArrayList<>();
        for(Comment comment : post.getComments()){
            CommentDTO commentDTO = mapCommentToCommentDTO(comment);
            commentDTOs.add(commentDTO);
        }
        postDTO.setComments(commentDTOs);
        return postDTO;
    }

    static public UserInfoDTO mapUserInfoToUserInfoDTO(UserInfo userInfo) {
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        BeanUtils.copyProperties(userInfo, userInfoDTO);
        userInfoDTO.setAvatar(userInfo.getAvatar() != null ? userInfo.getAvatar().getUrl() : null);
        userInfoDTO.setBackground(userInfo.getBackground() != null ? userInfo.getBackground().getUrl() : null);
        return userInfoDTO;
    }

    static public CommentDTO mapCommentToCommentDTO(Comment comment) {
        CommentDTO commentDTO = new CommentDTO();
        BeanUtils.copyProperties(comment, commentDTO);
        UserInfo userInfo = comment.getCommentedBy().getUserInfo();
        commentDTO.setCommentedByAvatar(userInfo.getAvatar().getUrl() != null ? userInfo.getAvatar().getUrl() : "");
        commentDTO.setCommentedByName(userInfo.getName());
        return commentDTO;
    }
}