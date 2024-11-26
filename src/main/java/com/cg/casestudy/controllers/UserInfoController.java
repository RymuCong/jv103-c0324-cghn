package com.cg.casestudy.controllers;

import com.cg.casestudy.dtos.PostRequest;
import com.cg.casestudy.dtos.UserInfoDTO;
import com.cg.casestudy.models.common.Image;
import com.cg.casestudy.models.user.User;
import com.cg.casestudy.models.user.UserInfo;
import com.cg.casestudy.services.*;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("/user")
public class UserInfoController {

    private final UserInfoService userInfoService;
    private final UserService userService;
    private final PostService postService;

    @Autowired
    public UserInfoController(UserInfoService userInfoService, UserService userService,
                              PostService postService) {
        this.userInfoService = userInfoService;
        this.userService = userService;
        this.postService = postService;
    }

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, new org.springframework.beans.propertyeditors.CustomDateEditor(dateFormat, true));
    }

    @GetMapping("/profile")
    public String showProfile(Model model){
        User currentUser = userService.getCurrentUser();
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("userInfo", userInfoService.getUserInfoByUser(currentUser));
        model.addAttribute("posts", postService.getPostsByUser(currentUser));
        model.addAttribute("newPost", new PostRequest());
        return "profile";
    }

    @PostMapping("/update_info")
    public String updateProfile(@Valid @ModelAttribute("userInfo") UserInfoDTO userInfoDTO,
                                BindingResult bindingResult,
                                RedirectAttributes redirectAttributes
    ){
        User currentUser = userService.getCurrentUser();
        //Chưa xử lí hiển thị lỗi cho người dùng
        if(bindingResult.hasErrors()){
            redirectAttributes.addFlashAttribute("org.springframework.validation.BindingResult.userInfo", bindingResult);
            redirectAttributes.addFlashAttribute("userInfo", userInfoDTO);
            redirectAttributes.addFlashAttribute("openEditUserInfoModal", true);
            return "redirect:/user/profile";
        }
        UserInfo userInfo = currentUser.getUserInfo();
        BeanUtils.copyProperties(userInfoDTO, userInfo);
        userInfoService.save(userInfo);
        return "redirect:/user/profile";

    }

    @PostMapping("/update_background")
    public String updateBackground(@RequestParam("background") MultipartFile backgroundImage, Model model){
        User currentUser = userService.getCurrentUser();
        try {
            userInfoService.updateBackground(backgroundImage, currentUser);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Lỗi tải ảnh lên");
            return "profile";
        }
        return "redirect:/user/profile";
    }

    @PostMapping("/update_avatar")
    public String updateAvatar(@RequestParam("avatar") MultipartFile avatarImage, Model model){
        User currentUser = userService.getCurrentUser();
        try {
            userInfoService.updateAvatar(avatarImage, currentUser);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Lỗi tải ảnh lên");
            return "profile";
        }
        return "redirect:/user/profile";
    }
}
