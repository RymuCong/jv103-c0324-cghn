package com.cg.casestudy.services;

import com.cg.casestudy.models.user.User;

public interface UserService {

    User findByEmail(String email);

    User save(User user);

    User getCurrentUser();
}
