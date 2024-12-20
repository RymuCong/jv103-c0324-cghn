package com.cg.casestudy.services;

import com.cg.casestudy.models.common.Image;

public interface ImageService {

    Image findImageByUrl(String url);

    void save(Image image);

    void delete(Image image);

}
