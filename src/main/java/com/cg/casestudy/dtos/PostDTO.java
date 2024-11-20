package com.cg.casestudy.dtos;

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
    private List<CommentDTO> comments;
    private List<Liked> likes;

    public boolean isLikedByUser(User currentUser){
        for (Liked liked : likes) {
            if (liked.getLikedBy().getId().equals(currentUser.getId())) {
                return true;
            }
        }
        return false;
    }

    // get time difference from current time and createdAt
    public String getTimeDifference() {
        return DateTimeUtils.getTimeDifference(createdAt);
    }

}
