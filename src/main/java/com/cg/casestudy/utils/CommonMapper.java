package com.cg.casestudy.utils;

import com.cg.casestudy.dtos.PostDTO;
import com.cg.casestudy.models.post.Post;
import org.springframework.beans.BeanUtils;

public class CommonMapper {

    static public <T> T map(Object source, Class<T> target) {
        try {
            T instance = target.newInstance();
            BeanUtils.copyProperties(source, instance);
            return instance;
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    // map Post to PostDTO
    static public PostDTO mapPostToPostDTO(Post post) {
        PostDTO postDTO = new PostDTO();
        BeanUtils.copyProperties(post, postDTO);
        postDTO.setImage(post.getImage() != null ? post.getImage().getUrl() : null);
        return postDTO;
    }
}
