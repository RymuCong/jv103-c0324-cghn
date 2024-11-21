package com.cg.casestudy.controllers;

import com.cg.casestudy.dtos.PostRequest;
import com.cg.casestudy.models.user.User;
import com.cg.casestudy.services.PostService;
import com.cg.casestudy.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


@Controller
public class PostController {

    private final PostService postService;

    private final UserService userService;

    @Autowired
    public PostController(PostService postService, UserService userService) {
        this.postService = postService;
        this.userService = userService;
    }

    @PostMapping("/post")
    public String save(@ModelAttribute PostRequest newPost, @RequestParam("file") MultipartFile file) {
        // Get the current user
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        User currentUser = userService.findByEmail(userDetails.getUsername());

        newPost.setCreatedBy(currentUser);

        postService.save(newPost, file);
        return "redirect:/home";
    }
}
