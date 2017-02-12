package com.homeenv.rest.api.dto;

import com.homeenv.domain.Image;
import com.homeenv.domain.ImageClassification;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ClassifiedImageDTO {

    private String path;

    private Map<String, Float> classifications;

    public ClassifiedImageDTO(Image image) {
        this.path = image.getPath();
        Set<ImageClassification> imageClassifications  = image.getImageClassifications();
        if (imageClassifications != null){
            classifications = new HashMap<>();
            imageClassifications.forEach(cls -> {
                String [] classes = StringUtils.split(cls.getClasses(),",");
                if (classes!= null && classes.length > 0){
                    for (int i = 0 ; i < classes.length ; i++){
                        classifications.put(classes[i], cls.getScore());
                    }
                }
            });
        }
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Map<String, Float> getClassifications() {
        return classifications;
    }

    public void setClassifications(Map<String, Float> classifications) {
        this.classifications = classifications;
    }

}
