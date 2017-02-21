package com.homeenv.rest.api;

import com.homeenv.Constants;
import com.homeenv.rest.api.dto.ClassifiedImageDTO;
import com.homeenv.service.ClassificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ClassifiedImagesResource {

    private final ClassificationService classificationService;

    @Autowired
    public ClassifiedImagesResource(ClassificationService classificationService) {
        this.classificationService = classificationService;
    }

    @RequestMapping(
            value = Constants.RestApi.Classification.CLASSIFICATIONS_ALL,
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<ClassifiedImageDTO>> findAllClassified(){
        return new ResponseEntity<>(classificationService.findAllClassified()
                .stream()
                .map(ClassifiedImageDTO::new)
                .collect(Collectors.toList()), HttpStatus.OK
        );
    }

    @RequestMapping(
            value = Constants.RestApi.Classification.CLASSIFICATIONS,
            method = RequestMethod.GET,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<List<ClassifiedImageDTO>> findClassified(@PathVariable(name = "from") Integer from, @PathVariable(name = "to") Integer to){
        return new ResponseEntity<>(classificationService.findClassified(from, to)
                .stream()
                .map(ClassifiedImageDTO::new)
                .collect(Collectors.toList()), HttpStatus.OK
        );
    }


}
