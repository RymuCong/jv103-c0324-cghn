package com.cg.casestudy.controllers;


import com.cg.casestudy.dtos.PostDTO;
import com.cg.casestudy.dtos.PostRequest;
import com.cg.casestudy.dtos.UserDTO;
import com.cg.casestudy.models.common.Image;
import com.cg.casestudy.models.post.Post;
import com.cg.casestudy.models.user.User;
import com.cg.casestudy.models.user.UserInfo;
import com.cg.casestudy.services.PostService;
import com.cg.casestudy.services.UserInfoService;
import com.cg.casestudy.services.UserService;
import com.cg.casestudy.services.impl.RoleService;
import com.cg.casestudy.utils.AppConstants;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Controller
public class UserController {

    private final UserService userService;
    private final UserInfoService userInfoService;
    private final PostService postService;
    private final RoleService roleService;
    //inject PasswordEncoder để mã hóa mật khẩu
    private final PasswordEncoder passwordEncoder;


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

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("newUser", new UserDTO());
        return "form-signup";
    }

    @PostMapping("/register")
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
        userInfo.setAvatar(Image.builder().url(AppConstants.defaultAvatar).build());
        userInfo.setBackground(Image.builder().url(AppConstants.defaultBackground).build());
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
        List<PostDTO> posts = postService.getAllPosts();
        model.addAttribute("posts", Objects.requireNonNullElse(posts, Collections.emptyList()));
        model.addAttribute("currentUser", currentUser);
        model.addAttribute("userInfo", userInfoService.getUserInfoByUser(currentUser));
        model.addAttribute("newPost", new PostRequest());

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

    @GetMapping("/logout")
    public String logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        if (authentication != null) {
            new SecurityContextLogoutHandler().logout(request, response, authentication);
        }
        return "redirect:/login";
    }
}
