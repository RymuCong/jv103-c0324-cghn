package com.cg.casestudy.controllers;

import com.cg.casestudy.dtos.UserInfoDTO;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @GetMapping("/profile")
    public String showProfile(Model model) {
        User currentUser = userService.getCurrentUser();
        UserInfoDTO userInfoDTO = new UserInfoDTO();
        //Chi set name neu chua co thong tin
        if(currentUser.getUserInfo().getDob() == null){
            userInfoDTO.setName(currentUser.getUsername());
        }
        else{
            BeanUtils.copyProperties(currentUser.getUserInfo(), userInfoDTO);
        }
        model.addAttribute("userInfo", userInfoDTO);
        return "profile";
    }

    @PostMapping("/edit")
    public String processEdit(@Valid @ModelAttribute("userInfo") UserInfoDTO userInfoDTO,
                              BindingResult bindingResult,
                              @RequestParam("avatarFile") MultipartFile avatarFile,
                              @RequestParam("backgroundFile") MultipartFile backgroundFile,
                              Model model
    ) {
        if(bindingResult.hasErrors()) {
            model.addAttribute("userInfo", userInfoDTO);
            model.addAttribute("showModal", true);
            return "profile";
        }

        User currentUser = userService.getCurrentUser();
        UserInfo userInfo = currentUser.getUserInfo();
        BeanUtils.copyProperties(userInfoDTO, userInfo);

        if(!avatarFile.isEmpty()){
            String imgUrl = userInfoService.uploadImageToFireBase(avatarFile);
            userInfo.getAvatar().setUrl(imgUrl);
        }

        if(!backgroundFile.isEmpty()){
            String imgUrl = userInfoService.uploadImageToFireBase(backgroundFile);
            userInfo.getBackground().setUrl(imgUrl);
        }

        userInfoService.save(userInfo);

        return "redirect:/user-info/edit";
    }
}
