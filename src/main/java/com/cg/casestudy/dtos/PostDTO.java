package com.cg.casestudy.dtos;

import com.cg.casestudy.models.post.Comment;
import com.cg.casestudy.models.user.User;
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
//    private String content;
    private User createdBy;
    private LocalDateTime createdAt;
    private String image;
    private List<Comment> latestComments;
    private int likes = 0;

    // get time difference from current time and createdAt
    public String getTimeDifference() {
        LocalDateTime now = LocalDateTime.now();
        long diff = java.time.Duration.between(createdAt, now).toMinutes();
        if (diff < 60) {
            return diff + " minutes ago";
        } else if (diff < 1440) {
            return diff / 60 + " hours ago";
        } else {
            return diff / 1440 + " days ago";
        }
    }

}
