package com.cg.casestudy.services.impl;

import com.cg.casestudy.dtos.UserInfoDTO;
import com.cg.casestudy.models.user.User;
import com.cg.casestudy.models.user.UserInfo;
import com.cg.casestudy.repositories.UserInfoRepository;
import com.cg.casestudy.services.FirebaseService;
import com.cg.casestudy.services.UserInfoService;
import com.cg.casestudy.utils.CommonMapper;
import com.google.firebase.cloud.StorageClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    private final UserInfoRepository userInfoRepository;

    @Autowired
    public UserInfoServiceImpl(UserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
    }


    @Override
    public UserInfoDTO getUserInfoByUser(User user) {
        UserInfo userInfo = userInfoRepository.findUserInfoByUser(user);
        return CommonMapper.mapUserInfoToUserInfoDTO(userInfo);
    }

    @Override
    public void save(UserInfo userInfo) {
        userInfoRepository.save(userInfo);
    }

}
