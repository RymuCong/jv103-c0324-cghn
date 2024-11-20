package com.cg.casestudy.services;

import com.cg.casestudy.models.user.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {

    User findByEmail(String email);

    User getCurrentUser();

    User save(User user);

}
