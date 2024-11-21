package com.cg.casestudy.services.impl;

import com.cg.casestudy.models.common.Image;
import com.cg.casestudy.repositories.ImageRepository;
import com.cg.casestudy.services.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ImageServiceImpl implements ImageService {

    private final ImageRepository imageRepository;

    @Autowired
    public ImageServiceImpl(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    @Override
    public Image findImageByUrl(String url) {
        return imageRepository.findImageByUrl(url);
    }

    @Override
    @Transactional
    public void delete(Image oldImage) {
        if (oldImage == null) {
            throw new IllegalArgumentException("Image to delete cannot be null");
        }

        Image imageFromDb = imageRepository.findById(oldImage.getId()).orElse(null);
        if (imageFromDb == null) {
            throw new IllegalArgumentException("Image to delete does not exist in the database");
        }

        if (oldImage.getUserImage() != null) {
            oldImage.setUserImage(null);
        }

        imageRepository.delete(oldImage);
    }
}
