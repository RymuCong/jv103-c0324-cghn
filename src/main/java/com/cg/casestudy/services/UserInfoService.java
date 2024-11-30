package com.cg.casestudy.services;

import com.cg.casestudy.dtos.SearchUserResponse;
import com.cg.casestudy.dtos.UserInfoDTO;
import com.cg.casestudy.models.user.User;
import com.cg.casestudy.models.user.UserInfo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserInfoService {

    UserInfoDTO getUserInfoByUser(User user);

    void save(UserInfo userInfo);

    void updateBackground(MultipartFile newImage, User currentUser) throws Exception;

    void updateAvatar(MultipartFile newImage, User currentUser) throws Exception;

    List<SearchUserResponse> searchUser(String keyword);
}
