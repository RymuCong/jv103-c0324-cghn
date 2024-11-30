package com.cg.casestudy.services.impl;

import com.cg.casestudy.dtos.NotificationRequest;
import com.cg.casestudy.dtos.NotificationResponse;
import com.cg.casestudy.models.common.Notification;
import com.cg.casestudy.models.user.User;
import com.cg.casestudy.repositories.NotificationRepository;
import com.cg.casestudy.repositories.UserRepository;
import com.cg.casestudy.services.NotificationService;
import com.cg.casestudy.utils.CommonMapper;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Override
    @Transactional
    public void sendNotification(NotificationRequest notificationRequest) {
        User user = userRepository.findById(notificationRequest.getUserId()).orElse(null);
        User userSend = userRepository.findById(notificationRequest.getUserSendId()).orElse(null);
        if (user != null && userSend != null) {
            //force to load user info before the session is closed (fix lazy loading issue)
            Hibernate.initialize(user.getUserInfo());
            Hibernate.initialize(userSend.getUserInfo());
            Notification newNotification = Notification.builder()
                    .message(notificationRequest.getMessage())
                    .user(user)
                    .userSend(userSend)
                    .createdAt(LocalDateTime.now())
                    .build();
            notificationRepository.save(newNotification); // Save notification to database
            // Send notification to user
            NotificationResponse response = CommonMapper.mapNotificationToNotificationResponse(newNotification);
            simpMessagingTemplate.convertAndSendToUser(String.valueOf(newNotification.getUser().getEmail()), "/topic/notification", response);
        }
    }

    @Override
    public List<NotificationResponse> getNotificationsByUserId(Long userId) {
        List<NotificationResponse> results = new ArrayList<>();
        List<Notification> notifications = notificationRepository.findByUserIdOrderByCreatedAtDesc(userId);
        List<Notification> topNotifications = notifications.size() > 3 ? notifications.subList(0, 3) : notifications;
        for(Notification notification : topNotifications){
            Hibernate.initialize(notification.getUser().getUserInfo());
            Hibernate.initialize(notification.getUserSend().getUserInfo());
            NotificationResponse response = CommonMapper.mapNotificationToNotificationResponse(notification);
            results.add(response);
        }
        return results;
    }
}