package com.homeenv.service;

import com.homeenv.config.ApplicationProperties;
import com.homeenv.messaging.IndexingRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessagingService {

    Logger log = LoggerFactory.getLogger(MessagingService.class);

    private final RabbitTemplate rabbitTemplate;

    private final ApplicationProperties applicationProperties;

    @Autowired
    public MessagingService(RabbitTemplate rabbitTemplate, ApplicationProperties applicationProperties) {
        this.rabbitTemplate = rabbitTemplate;
        this.applicationProperties = applicationProperties;
    }

    public void sendIndexingRequest(final IndexingRequest request){
        try {
            rabbitTemplate.convertAndSend(applicationProperties.getRabbit().getQueueIndexingRequests(),request);
        } catch (Exception e){
            log.error("unable to send classification message ",e);
        }

    }
}
