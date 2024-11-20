package com.cg.casestudy.dtos;

import com.cg.casestudy.utils.DateTimeUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {
    private Long id;
    private String content;
    private Long postId;
    private String commentedByName;
    private String commentedByAvatar;
    private LocalDateTime createdAt;

    public String getTimeDifference() {
        return DateTimeUtils.getTimeDifference(createdAt);
    }
}
