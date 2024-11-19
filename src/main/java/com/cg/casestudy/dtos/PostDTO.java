package com.cg.casestudy.dtos;

import com.cg.casestudy.models.post.Comment;
import com.cg.casestudy.models.post.Liked;
import com.cg.casestudy.models.user.User;
import com.cg.casestudy.utils.DateTimeUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDTO {
    private Long id;
    private String title;
    private User createdBy;
    private LocalDateTime createdAt;
    private String image;
    private List<Comment> comments;
    private List<Liked> likes;

    // get time difference from current time and createdAt
    public String getTimeDifference() {
        return DateTimeUtils.getTimeDifference(createdAt);
    }

}
