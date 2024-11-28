package com.cg.casestudy.dtos;

import com.cg.casestudy.validation.Age;
import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class NotificationRequest {

    private String message;
    private Long userId;
    private Long userSendId;
    private String type;

}
