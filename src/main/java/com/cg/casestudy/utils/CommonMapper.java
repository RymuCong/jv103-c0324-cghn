package com.cg.casestudy.utils;

import com.cg.casestudy.dtos.CommentDTO;
import com.cg.casestudy.dtos.PostDTO;
import com.cg.casestudy.dtos.PostRequest;
import com.cg.casestudy.dtos.UserInfoDTO;
import com.cg.casestudy.models.post.Comment;
import com.cg.casestudy.models.post.Post;
import com.cg.casestudy.models.user.UserInfo;
import org.springframework.beans.BeanUtils;

import java.time.LocalDate;
import java.time.LocalDateTime;
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

    // map PostRequest to Post
    static public Post mapPostRequestToPost(PostRequest postRequest) {
        Post post = new Post();
        post.setTitle(postRequest.getTitle());
        post.setCreatedBy(postRequest.getCreatedBy());
        post.setCreatedAt(LocalDateTime.now());
        post.setLikes(new ArrayList<>());
        post.setComments(new ArrayList<>());
        return post;
    }

    static public UserInfoDTO mapUserInfoToUserInfoDTO(UserInfo userInfo) {
        UserInfoDTO userInfoDTO = new UserInfoDTO();
//        BeanUtils.copyProperties(userInfo, userInfoDTO);
        userInfoDTO.setId(userInfo.getId());
        userInfoDTO.setName(userInfo.getName());
        userInfoDTO.setGender(userInfo.getGender());
        userInfoDTO.setDob(userInfo.getDob());
        userInfoDTO.setEducation(userInfo.getEducation());
        userInfoDTO.setLocation(userInfo.getLocation());
        userInfoDTO.setDescription(userInfo.getDescription());
        userInfoDTO.setAvatar(userInfo.getAvatar() != null ? userInfo.getAvatar().getUrl() : null);
        userInfoDTO.setBackground(userInfo.getBackground() != null ? userInfo.getBackground().getUrl() : null);
        return userInfoDTO;
    }

    static public CommentDTO mapCommentToCommentDTO(Comment comment) {
        CommentDTO commentDTO = new CommentDTO();
        BeanUtils.copyProperties(comment, commentDTO);
        UserInfo userInfo = comment.getCommentedBy().getUserInfo();
        if (userInfo != null) {
            commentDTO.setCommentedByAvatar(userInfo.getAvatar() != null ? userInfo.getAvatar().getUrl() : "");
            commentDTO.setCommentedByName(userInfo.getName());
        } else {
            commentDTO.setCommentedByAvatar("");
            commentDTO.setCommentedByName("Unknown");
        }
        commentDTO.setPostId(comment.getPost().getId());
        return commentDTO;
    }
}
