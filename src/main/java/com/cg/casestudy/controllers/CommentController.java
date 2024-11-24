package com.cg.casestudy.controllers;

import com.cg.casestudy.dtos.CommentDTO;
import com.cg.casestudy.models.user.User;
import com.cg.casestudy.services.CommentService;
import com.cg.casestudy.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api")
public class CommentController {

    private final CommentService commentService;

    private final UserService userService;

    @Autowired
    public CommentController(CommentService commentService, UserService userService) {
        this.commentService = commentService;
        this.userService = userService;
    }

    @MessageMapping("/user.addComment")
    @SendTo("/topic/add_comment")
    public CommentDTO sendComment(@Payload @Valid CommentDTO data, Principal principal) {
        User currentUser = userService.findByEmail(principal.getName());
        return commentService.addComment(data.getPostId(), data, currentUser.getId());
    }

    @MessageMapping("/user.getUser")
    @SendTo("/topic/add_comment")
    public User getUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return userService.findByEmail(userDetails.getUsername());
    }

    // Add this method to your CommentController.java
    @RequestMapping("/api/currentUser")
    public ResponseEntity<String> getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        return ResponseEntity.ok(userDetails.getUsername());
    }
}
