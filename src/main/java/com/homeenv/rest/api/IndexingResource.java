package com.homeenv.rest.api;

import com.homeenv.Constants;
import com.homeenv.messaging.IndexingRequest;
import com.homeenv.service.ImageMetadataService;
import com.homeenv.service.MessagingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexingResource {

    private final ImageMetadataService metadataService;

    private final MessagingService messagingService;

    @Autowired
    public IndexingResource(ImageMetadataService metadataService,
                            MessagingService messagingService) {
        this.metadataService = metadataService;
        this.messagingService = messagingService;
    }

    @RequestMapping(
            value = Constants.RestApi.Indexing.INDEX_METADATA,
            method = RequestMethod.GET
    )
    public ResponseEntity<Void> indexMetadata(){
//        metadataService.indexStorage();
            messagingService.sendIndexingRequest(new IndexingRequest("test"));

        return ResponseEntity.ok().build();
    }
}
