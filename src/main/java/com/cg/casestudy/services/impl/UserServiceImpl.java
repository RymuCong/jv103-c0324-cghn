package com.cg.casestudy.services.impl;

import com.cg.casestudy.models.user.User;
import com.cg.casestudy.repositories.UserRepository;
import com.cg.casestudy.security.CustomUserDetails;
import com.cg.casestudy.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{

    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User save(User user) {
        return userRepository.save(user);
    }

    @Override
    public User getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String email = ((CustomUserDetails) principal).getUsername(); // email is the username
        return findByEmail(email);
    }

}
