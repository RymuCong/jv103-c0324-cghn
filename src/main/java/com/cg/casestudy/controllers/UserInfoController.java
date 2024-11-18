package com.cg.casestudy.controllers;

import com.cg.casestudy.dtos.UserInfoDTO;
import com.cg.casestudy.models.common.Image;
import com.cg.casestudy.models.user.User;
import com.cg.casestudy.models.user.UserInfo;
import com.cg.casestudy.services.UserInfoService;
import com.cg.casestudy.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("/user")
public class UserInfoController {


    private final UserInfoService userInfoService;
    private final UserService userService;

    @Autowired
    public UserInfoController(UserInfoService userInfoService, UserService userService) {
        this.userInfoService = userInfoService;
        this.userService = userService;
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
        model.addAttribute("userInfo", currentUser.getUserInfo());
        model.addAttribute("user", currentUser);
        return "profile";
    }

    @PostMapping("/update_info")
    public String updateProfile(@Valid @ModelAttribute("userInfo") UserInfoDTO userInfoDTO,
                                BindingResult bindingResult, Model model){
        User currentUser = userService.getCurrentUser();
        if(bindingResult.hasErrors()){
            //error
            model.addAttribute("user", currentUser);
            model.addAttribute("userInfo", currentUser.getUserInfo());
            return "profile";
        }
        UserInfo userInfo = currentUser.getUserInfo();
        BeanUtils.copyProperties(userInfoDTO, userInfo);
        userInfoService.save(userInfo);
        return "redirect:/user/profile";

    }

    @PostMapping("/update_background")
    public String updateBackground(@RequestParam("background") MultipartFile backgroundImage, Model model){
        User currentUser = userService.getCurrentUser();
        if(!backgroundImage.isEmpty()){
            try{
                String url = userInfoService.uploadImageToFireBase(backgroundImage);
                //Tao anh moi voi url vua upload
                Image newBackground = Image.builder().url(url).build();
                //Cap nhat anh nen moi cho user hien tai
                currentUser.getUserInfo().setBackground(newBackground);
                userService.save(currentUser);
            } catch (Exception e){
                model.addAttribute("errorMessage", "Lỗi tải ảnh lên");
                return "profile";
            }
        }
        return "redirect:/user/profile";
    }

    @PostMapping("/update_avatar")
    public String updateAvatar(@RequestParam("avatar") MultipartFile avatarImage, Model model){
        User currentUser = userService.getCurrentUser();
        if(!avatarImage.isEmpty()){
            try {
                String url = userInfoService.uploadImageToFireBase(avatarImage);
                //Tao anh moi voi url vua upload
                Image newAvatar = Image.builder().url(url).build();
                //Cap nhat anh dai dien moi cho user hien tai
                currentUser.getUserInfo().setAvatar(newAvatar);
                userService.save(currentUser);
            } catch (Exception e) {
                model.addAttribute("errorMessage", "Lỗi tải ảnh lên");
                return "profile";
            }
        }
        return "redirect:/user/profile";
    }

}
