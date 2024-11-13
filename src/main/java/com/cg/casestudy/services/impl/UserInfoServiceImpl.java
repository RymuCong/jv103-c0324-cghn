package com.cg.casestudy.services.impl;

import com.cg.casestudy.models.user.UserInfo;
import com.cg.casestudy.repositories.UserInfoRepository;
import com.cg.casestudy.services.UserInfoService;
import com.google.firebase.cloud.StorageClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    private final UserInfoRepository userInfoRepository;
    private final StorageClient storageClient;

    @Autowired
    public UserInfoServiceImpl(UserInfoRepository userInfoRepository, StorageClient storageClient) {
        this.userInfoRepository = userInfoRepository;
        this.storageClient = storageClient;
    }


    @Override
    public void save(UserInfo userInfo) {
        userInfoRepository.save(userInfo);
    }

    @Override
    public String uploadImageToFireBase(MultipartFile file) {
        String fileName = UUID.randomUUID() + "-" + file.getOriginalFilename();
        try {
            storageClient.bucket().create(fileName, file.getInputStream(), file.getContentType());
            return storageClient.bucket().get(fileName).getMediaLink();
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
}
