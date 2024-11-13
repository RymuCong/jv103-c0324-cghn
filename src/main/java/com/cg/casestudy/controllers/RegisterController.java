package com.cg.casestudy.controllers;

import com.cg.casestudy.models.user.User;
import com.cg.casestudy.models.user.UserInfo;
import com.cg.casestudy.services.UserInfoService;
import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.ui.Model;
import com.cg.casestudy.dtos.RegisterUserDTO;
import com.cg.casestudy.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/register")
public class RegisterController {

    private final UserService userService;
    private final UserInfoService userInfoService;
    //inject PasswordEncoder để mã hóa mật khẩu
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegisterController(UserService userService,
                                UserInfoService userInfoService,
                              PasswordEncoder passwordEncoder
    ) {
        this.userService = userService;
        this.userInfoService = userInfoService;
        this.passwordEncoder = passwordEncoder;

    }

    //xóa khoảng trắng 2 đầu  trong tên, email, password
    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {

        StringTrimmerEditor stringTrimmerEditor = new StringTrimmerEditor(true);

        dataBinder.registerCustomEditor(String.class, stringTrimmerEditor);
    }

    @GetMapping
    public String showRegisterForm(Model model) {
        model.addAttribute("newUser", new RegisterUserDTO());
        return "form-signup";
    }

    @PostMapping
    public String processRegister(@Valid @ModelAttribute("newUser") RegisterUserDTO registerUserDTO, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()){
            return "form-signup";
        }
        if(!registerUserDTO.getPassword().equals(registerUserDTO.getConfirmPassword())){
            model.addAttribute("confirmError", "Xác nhận mật khẩu không khớp");
            return "form-signup";
        }
        if(userService.findByEmail(registerUserDTO.getEmail()) != null){
            model.addAttribute("existedEmailError", "Email đã đăng ký cho tài khoản khác");
            return "form-signup";
        }
        //tạo mới user
        User user = new User();
        BeanUtils.copyProperties(registerUserDTO, user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        //tạo mới userInfo
        UserInfo userInfo = new UserInfo();
        userInfo.setName(registerUserDTO.getUsername());
        userInfoService.save(userInfo);

        user.setUserInfo(userInfo);
        userService.save(user);


        return "redirect:/user/profile";

    }
}
