package com.cg.casestudy.controllers;


import com.cg.casestudy.dtos.UserDTO;
import com.cg.casestudy.models.common.Image;
import com.cg.casestudy.models.user.User;
import com.cg.casestudy.models.user.UserInfo;
import com.cg.casestudy.services.PostService;
import com.cg.casestudy.services.UserInfoService;
import com.cg.casestudy.services.UserService;
import com.cg.casestudy.services.impl.PostServiceImpl;
import com.cg.casestudy.services.impl.RoleService;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@Controller
public class UserController {

    private final UserService userService;
    private final UserInfoService userInfoService;
    private final PostService postService;
    private final RoleService roleService;
    //inject PasswordEncoder để mã hóa mật khẩu
    private final PasswordEncoder passwordEncoder;


    @Value("${userinfo.default.avatarUrl}")
    private String defaultAvatarUrl;

    @Value("${userinfo.default.backgroundUrl}")
    private String defaultBackgroundUrl;

    @Autowired
    public UserController(UserService userService,
                              UserInfoService userInfoService,
                              PostService postService,
                              PasswordEncoder passwordEncoder,
                              RoleService roleService
    ) {
        this.userService = userService;
        this.userInfoService = userInfoService;
        this.postService = postService;
        this.passwordEncoder = passwordEncoder;
        this.roleService = roleService;
    }

    //xóa khoảng trắng 2 đầu  trong tên, email, password
    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {

        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);

        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping
    public String showRegisterForm(Model model) {
        model.addAttribute("newUser", new UserDTO());
        return "form-signup";
    }

    @PostMapping
    public String processRegister(@Valid @ModelAttribute("newUser") UserDTO userDTO, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()){
            return "form-signup";
        }
        if(!userDTO.getPassword().equals(userDTO.getConfirmPassword())){
            model.addAttribute("confirmError", "Xác nhận mật khẩu không khớp");
            return "form-signup";
        }
        if(userService.findByEmail(userDTO.getEmail()) != null){
            model.addAttribute("existedEmailError", "Email đã đăng ký cho tài khoản khác");
            return "form-signup";
        }
        //tạo mới user
        User user = new User();
        BeanUtils.copyProperties(userDTO, user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setRoles(Collections.singletonList(roleService.findRoleByName("ROLE_USER")));

        //tạo mới userInfo
        UserInfo userInfo = new UserInfo();
        userInfo.setName(userDTO.getUsername());
        userInfo.setGender(true);
        userInfo.setAvatar(Image.builder().url(defaultAvatarUrl).build());
        userInfo.setBackground(Image.builder().url(defaultBackgroundUrl).build());
        userInfoService.save(userInfo);

        user.setUserInfo(userInfo);
        userService.save(user);

        return "redirect:/home";

    }

    @GetMapping("/login")
    public String showLoginPage(){
        return "form-login";
    }

    @GetMapping("/access-denied")
    public String accessDenied(){
        return "access-denied";
    }

    @GetMapping("/home")
    public String showHomePage(Model model) {
        User currentUser = userService.getCurrentUser();
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("userInfo", userInfoService.getUserInfoByUser(currentUser));
        model.addAttribute("posts", postService.getAllPosts());
        return "feeds";
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
