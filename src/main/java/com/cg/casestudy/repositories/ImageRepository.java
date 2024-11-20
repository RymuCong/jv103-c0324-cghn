package com.cg.casestudy.repositories;

import com.cg.casestudy.models.common.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Long> {
    Image findImageByUrl(String url);
}
