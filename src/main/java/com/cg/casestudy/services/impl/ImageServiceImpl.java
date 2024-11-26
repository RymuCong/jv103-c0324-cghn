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
  
    @Transactional
    public void save(Image image) {
        imageRepository.save(image);
    }

    @Transactional
    public void delete(Image image) {
        imageRepository.delete(image);

    @Override
    public void delete(Image oldImage) {
        imageRepository.delete(oldImage);
    }
}
