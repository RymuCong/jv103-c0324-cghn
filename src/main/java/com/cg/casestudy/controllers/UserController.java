package com.cg.casestudy.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {


    @GetMapping("/home")
    public String showHomePage() {
        return "home";
    }

    @GetMapping("/admin/home")
    public String showAdminHomePage() {
        return "adminPage";
    }

    @GetMapping("/user/images")
    public String showImages() {
        return "your-images";
    }

    @GetMapping("/user/friends")
    public String showFriends() {
        return "your-friends";
    }
}
