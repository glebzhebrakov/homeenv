package com.homeenv.service;

import com.google.common.collect.Lists;
import com.homeenv.domain.Image;
import com.homeenv.repository.ImageClassesRepository;
import com.homeenv.repository.ImageClassificationRepository;
import com.homeenv.repository.ImageRepository;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClassificationService {

    private final ImageRepository imageRepository;
    private final ImageClassificationRepository imageClassificationRepository;
    private final ImageClassesRepository imageClassesRepository;

    @Autowired
    public ClassificationService(ImageRepository imageRepository,
                                 ImageClassificationRepository imageClassificationRepository,
                                 ImageClassesRepository imageClassesRepository) {
        this.imageRepository = imageRepository;
        this.imageClassificationRepository = imageClassificationRepository;
        this.imageClassesRepository = imageClassesRepository;
    }

    public List<Image> findAllClassified(){
        return imageRepository.findClassified();
    }

    public List<Image> findClassified(Integer from, Integer to){
        return imageRepository.findClassifiedPageable(new PageRequest(from, to)).getContent();
    }

    public List<Image> findClassifiedByClass(String cls, Integer from, Integer to){
        return imageRepository.findClassifiedPageableByClass(cls,new PageRequest(from, to)).getContent();
    }

    public List<String> findClassifications(){
        List<String> rawClasses = new ArrayList<>();
        imageClassesRepository
                .findOrderedPossibleClassifications()
                .forEach(cls -> rawClasses.addAll(Lists.newArrayList(StringUtils.split(cls, ','))));

        Collections.sort(rawClasses);
        return rawClasses.stream().map(cls -> StringUtils.capitalize(cls.trim())).collect(Collectors.toList());
    }
}


