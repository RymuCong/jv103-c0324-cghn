package com.cg.casestudy.controllers;

import com.cg.casestudy.dtos.NotificationRequest;
import com.cg.casestudy.models.user.Relationship;
import com.cg.casestudy.models.user.User;
import com.cg.casestudy.services.NotificationService;
import com.cg.casestudy.services.UserService;
import com.cg.casestudy.services.RelationshipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class RelationshipController {

    private final RelationshipService relationshipService;
    private final UserService userService;
    private final NotificationService notificationService;

    @Autowired
    public RelationshipController(RelationshipService relationshipService, UserService userService, NotificationService notificationService) {
        this.relationshipService = relationshipService;
        this.userService = userService;
        this.notificationService = notificationService;
    }

    @MessageMapping("/friend.add")
    @SendTo("/topic/friendRequest")
    public void addFriend(@Payload String friendId) {
        Relationship relationship = new Relationship();
        User userOne = userService.getCurrentUser();
        User userTwo = userService.findById(Long.parseLong(friendId));
        relationship.setUserOne(userOne);
        relationship.setUserTwo(userTwo);
        relationshipService.addFriend(relationship);

        NotificationRequest notificationRequest = NotificationRequest.builder()
                .message("You have a new friend request from " + userOne.getUsername())
                .userId(userTwo.getId())
                .userSendId(userOne.getId())
                .type("friendRequest")
                .build();
        notificationService.sendNotification(notificationRequest);
    }
}