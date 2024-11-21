package com.cg.casestudy.services;

import com.cg.casestudy.dtos.UserInfoDTO;
import com.cg.casestudy.models.user.User;
import com.cg.casestudy.models.user.UserInfo;
import org.springframework.web.multipart.MultipartFile;

public interface UserInfoService {

    UserInfoDTO getUserInfoByUser(User user);

    void save(UserInfo userInfo);

    String uploadImageToFireBase(MultipartFile file);

    Boolean deleteImageFromFireBase(String url);


}
