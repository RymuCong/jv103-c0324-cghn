package com.cg.casestudy.controllers;

import org.springframework.ui.Model;
import com.cg.casestudy.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/newsfeed")
public class NewsfeedController {

    private final PostService postService;

    @Autowired
    public NewsfeedController(PostService postService) {
        this.postService = postService;
    }

    @GetMapping()
    public String showNewsfeedPage(Model model) {
        model.addAttribute("posts", postService.getAllPosts());
        return "feeds";
    }
}
