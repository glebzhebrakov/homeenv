package com.homeenv.rest.api;

import com.homeenv.Constants;
import com.homeenv.service.ImageMetadataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexingResource {

    private final ImageMetadataService metadataService;

    @Autowired
    public IndexingResource(ImageMetadataService metadataService) {
        this.metadataService = metadataService;
    }

    @RequestMapping(
            value = Constants.RestApi.Indexing.INDEX_METADATA,
            method = RequestMethod.GET
    )
    public ResponseEntity<Void> indexMetadata(){
        metadataService.indexStorage();
        return ResponseEntity.ok().build();
    }
}
