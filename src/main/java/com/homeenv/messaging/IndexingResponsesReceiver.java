package com.homeenv.messaging;

import com.google.gson.Gson;
import com.homeenv.config.ApplicationProperties;
import com.homeenv.domain.ImageClassification;
import com.homeenv.repository.ImageClassificationRepository;
import com.homeenv.repository.ImageRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

import static org.apache.commons.lang.StringUtils.isNotBlank;

@Component
public class IndexingResponsesReceiver {

    private static final Logger log = LoggerFactory.getLogger(IndexingResponsesReceiver.class);

    private final RabbitTemplate rabbitTemplate;
    private final ApplicationProperties applicationProperties;
    private final ImageRepository imageRepository;
    private final ImageClassificationRepository imageClassificationRepository;

    public IndexingResponsesReceiver(RabbitTemplate rabbitTemplate,
                                     ApplicationProperties applicationProperties,
                                     ImageRepository imageRepository,
                                     ImageClassificationRepository imageClassificationRepository) {
        this.rabbitTemplate = rabbitTemplate;
        this.applicationProperties = applicationProperties;
        this.imageRepository = imageRepository;
        this.imageClassificationRepository = imageClassificationRepository;
    }

    public void consumeMessage(Object message){
        try {
            Gson gson = new Gson();
            IndexingResponse response = gson.fromJson(new String((byte[]) message), IndexingResponse.class);
            log.info("Classified: " + response.getPath());
            imageRepository.findByHash(response.getHash()).ifPresent(image -> {

                if (isNotBlank(response.getError()) || response.getClassificationResult() == null){
                    log.info(String.format("Indexing error : %s ", response.getError()));

                    if (isNotBlank(response.getError())){
                        image.setError(response.getError());
                    } else {
                        image.setError("Empty error and classification result");
                    }

                    imageRepository.save(image);

                } else {
                    Set<ImageClassification> classifications = new HashSet<>(response.getClassificationResult().size());
                    response.getClassificationResult().forEach((key, value) -> classifications.add(new ImageClassification(image, key, Float.valueOf(value))));
                    if (!classifications.isEmpty()){
                        imageClassificationRepository.save(classifications);
                        image.setIndexed(true);
                        imageRepository.save(image);
                    }
                }


            });

        } catch (Exception e){
            log.error("unable to save classification result", e);
        }

    }
}
