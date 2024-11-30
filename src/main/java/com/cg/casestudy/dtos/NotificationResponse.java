package com.cg.casestudy.dtos;

import com.cg.casestudy.utils.DateTimeUtils;
import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class NotificationResponse {

    private Long id;

    private String message;

    private String senderName;

    private String senderAvatar;

    private LocalDateTime createdAt;

    public String getTimeDifference() {
        return DateTimeUtils.getTimeDifference(createdAt);
    }
}
