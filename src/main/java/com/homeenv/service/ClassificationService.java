package com.homeenv.service;

import com.homeenv.domain.Image;
import com.homeenv.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassificationService {

    private final ImageRepository imageRepository;

    @Autowired
    public ClassificationService(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public List<Image> findAllClassified(){
        return imageRepository.findClassified();
    }
}


