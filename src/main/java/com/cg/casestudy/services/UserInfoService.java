package com.cg.casestudy.services;

import com.cg.casestudy.models.user.UserInfo;
import org.springframework.web.multipart.MultipartFile;

public interface UserInfoService {

    void save(UserInfo userInfo);

    String uploadImageToFireBase(MultipartFile file);
}
