package com.cg.casestudy.services;

import com.cg.casestudy.dtos.NotificationRequest;
import com.cg.casestudy.dtos.NotificationResponse;

import java.util.List;

public interface NotificationService {
    void sendNotification(NotificationRequest notificationRequest);

    List<NotificationResponse> getNotificationsByUserId(Long userId);
}
