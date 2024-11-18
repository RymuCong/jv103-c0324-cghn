package com.cg.casestudy.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class UserController {


    @GetMapping("/home")
    public String showHomePage() {
        return "feeds";
    }

    @GetMapping("/admin/home")
    public String showAdminHomePage() {
        return "adminPage";
    }

}
