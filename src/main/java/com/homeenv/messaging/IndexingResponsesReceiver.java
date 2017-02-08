package com.homeenv.messaging;

import com.homeenv.config.ApplicationProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class IndexingResponsesReceiver {

    private final RabbitTemplate rabbitTemplate;
    private final ApplicationProperties applicationProperties;

    public IndexingResponsesReceiver(RabbitTemplate rabbitTemplate, ApplicationProperties applicationProperties) {
        this.rabbitTemplate = rabbitTemplate;
        this.applicationProperties = applicationProperties;
    }

    public void consumeMessage(Object message){
        System.out.println(message);
    }
}
