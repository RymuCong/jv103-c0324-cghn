package com.cg.casestudy.services.impl;

import com.cg.casestudy.dtos.UserInfoDTO;
import com.cg.casestudy.models.common.Image;
import com.cg.casestudy.models.user.User;
import com.cg.casestudy.models.user.UserInfo;
import com.cg.casestudy.repositories.UserInfoRepository;
import com.cg.casestudy.services.FirebaseService;
import com.cg.casestudy.services.ImageService;
import com.cg.casestudy.services.UserInfoService;
import com.cg.casestudy.services.UserService;
import com.cg.casestudy.utils.CommonMapper;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UserInfoServiceImpl implements UserInfoService {

    private final UserInfoRepository userInfoRepository;
    private final FirebaseService firebaseService;
    private final ImageService imageService;

    @Autowired
    public UserInfoServiceImpl(UserInfoRepository userInfoRepository,
                               FirebaseService firebaseService, ImageService imageService) {
        this.userInfoRepository = userInfoRepository;
        this.firebaseService = firebaseService;
        this.imageService = imageService;
    }

    @Override
    public UserInfoDTO getUserInfoByUser(User user) {
        UserInfo userInfo = userInfoRepository.findUserInfoByUser(user);
        return CommonMapper.mapUserInfoToUserInfoDTO(userInfo);
    }

    @Transactional
    @Override
    public void save(UserInfo userInfo) {
        userInfoRepository.save(userInfo);
    }

    @Override
    @Transactional
    public void updateBackground(MultipartFile newImage, User currentUser) {
        if (!newImage.isEmpty()) {
            UserInfo userInfo = currentUser.getUserInfo();
            Image oldBackground = userInfo.getBackground();
            if (oldBackground != null) {
                userInfo.setBackground(null); // Set background to null to avoid foreign key constraint
                firebaseService.deleteImageFromFireBase(oldBackground.getUrl());
                oldBackground.markAsDeleted();
                imageService.save(oldBackground);
            }
            // Upload new background to firebase and save the url to database
            String urlImage = firebaseService.uploadImageToFireBase(newImage);
            Image newBackground = Image.builder().url(urlImage).build();
            newBackground.setUserImage(currentUser);
            // Set new background to user
            userInfo.setBackground(newBackground);
            currentUser.getImages().add(newBackground);
            userInfoRepository.save(userInfo);
        }
    }

    @Override
    @Transactional
    public void updateAvatar(MultipartFile newImage, User currentUser) {
        if (!newImage.isEmpty()) {
            UserInfo userInfo = currentUser.getUserInfo();
            Image oldAvatar = userInfo.getAvatar();
            if (oldAvatar != null) {
                userInfo.setAvatar(null); // Set avatar to null to avoid foreign key constraint
                firebaseService.deleteImageFromFireBase(oldAvatar.getUrl());
                oldAvatar.markAsDeleted();
                imageService.save(oldAvatar);
            }
            // Upload new avatar to firebase and save the url to database
            String url = firebaseService.uploadImageToFireBase(newImage);
            Image newAvatar = Image.builder().url(url).build();
            newAvatar.setUserImage(currentUser);
            // Set new avatar to user
            userInfo.setAvatar(newAvatar);
            currentUser.getImages().add(newAvatar);
            userInfoRepository.save(userInfo);
        }
    }
}