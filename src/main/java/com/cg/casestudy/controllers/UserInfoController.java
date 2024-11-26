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
    private final ImageService imageService;
    private final FirebaseService firebaseService;

    @Autowired
    public UserInfoController(UserInfoService userInfoService, UserService userService,
                              PostService postService, ImageService imageService, FirebaseService firebaseService) {
        this.userInfoService = userInfoService;
        this.userService = userService;
        this.postService = postService;
        this.imageService = imageService;
        this.firebaseService = firebaseService;
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
                                BindingResult bindingResult, Model model,
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
        if(!backgroundImage.isEmpty()){
            try{
                UserInfo userInfo = currentUser.getUserInfo();
                Image oldBackground = userInfo.getBackground();
                if(oldBackground != null){
                    userInfo.setBackground(null); // Set background to null to avoid foreign key constraint
//                    userService.save(currentUser); // Save user to update the foreign key
                    firebaseService.deleteImageFromFireBase(oldBackground.getUrl());
                    imageService.delete(oldBackground);
                    currentUser.getImages().remove(oldBackground);
                }
                // Upload new background to firebase and save the url to database
                String urlImage = firebaseService.uploadImageToFireBase(backgroundImage);
                Image newBackground = Image.builder().url(urlImage).build();
                newBackground.setUserImage(currentUser);
                // Set new background to user
                currentUser.getUserInfo().setBackground(newBackground);
                currentUser.getImages().add(newBackground);
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
                UserInfo userInfo = currentUser.getUserInfo();
                Image oldAvatar = userInfo.getAvatar();
                if(oldAvatar != null){
                    userInfo.setAvatar(null); // Set avatar to null to avoid foreign key constraint
                    userService.save(currentUser); // Save user to update the foreign key
                    firebaseService.deleteImageFromFireBase(oldAvatar.getUrl());
                    imageService.delete(oldAvatar);
                    currentUser.getImages().remove(oldAvatar);
                }
                // Upload new avatar to firebase and save the url to database
                String url = firebaseService.uploadImageToFireBase(avatarImage);
                Image newAvatar = Image.builder().url(url).build();
                newAvatar.setUserImage(currentUser);
                // Set new avatar to user
                currentUser.getUserInfo().setAvatar(newAvatar);
                currentUser.getImages().add(newAvatar);
                userService.save(currentUser);
            } catch (Exception e) {
                model.addAttribute("errorMessage", "Lỗi tải ảnh lên");
                return "profile";
            }
        }
        return "redirect:/user/profile";
    }
}
