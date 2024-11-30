package com.cg.casestudy.controllers;

import com.cg.casestudy.dtos.LikeResponse;
import com.cg.casestudy.dtos.NotificationRequest;
import com.cg.casestudy.dtos.PostDTO;
import com.cg.casestudy.dtos.PostRequest;
import com.cg.casestudy.models.user.User;
import com.cg.casestudy.services.NotificationService;
import com.cg.casestudy.services.PostService;
import com.cg.casestudy.services.UserInfoService;
import com.cg.casestudy.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.Objects;


@Controller
public class PostController {

    private final PostService postService;

    private final UserService userService;

    private final NotificationService notificationService;

    private final UserInfoService userInfoService;

    @Autowired
    public PostController(PostService postService, UserService userService,
                          NotificationService notificationService, UserInfoService userInfoService) {
        this.postService = postService;
        this.userService = userService;
        this.notificationService = notificationService;
        this.userInfoService = userInfoService;
    }

    @PostMapping("/post")
    public String save(@ModelAttribute PostRequest newPost, @RequestParam("file") MultipartFile file) {
        // Get the current user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User currentUser = userService.findByEmail(userDetails.getUsername());

        newPost.setCreatedBy(currentUser);
        if(Objects.requireNonNull(file.getOriginalFilename()).isEmpty()) {
            file = null;
        }
        postService.save(newPost, file);
        return "redirect:/home";
    }

    @MessageMapping("/user.likePost")
    @SendTo("/topic/like_post")
    public LikeResponse likePost(@Payload Long postId, Principal principal) {
        User currentUser = userService.findByEmail(principal.getName());
        PostDTO postDTO = postService.likePost(postId, currentUser.getId());
        Long postIdResponse = postDTO.getId();
        int likeCount = postDTO.getLikes().size();
        if(!postDTO.getCreatedBy().getId().equals(currentUser.getId())) {
            NotificationRequest notificationRequest = NotificationRequest.builder()
                    .message(" đã thích bài viết")
                    .userId(postDTO.getCreatedBy().getId())
                    .userSendId(currentUser.getId())
                    .type("like")
                    .build();
            notificationService.sendNotification(notificationRequest);
        }
        return new LikeResponse(postIdResponse, likeCount);
    }

    @GetMapping("/post_details/{postId}")
    public String showPostDetails(@PathVariable("postId") Long postId, Model model) {
        PostDTO postDTO = postService.findById(postId);
        model.addAttribute("post", postDTO);
        User currentUser = userService.getCurrentUser();
        model.addAttribute("userInfo", userInfoService.getUserInfoByUser(currentUser));
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("notifications", notificationService.getNotificationsByUserId(currentUser.getId()));
        return "post-details";
    }
}
