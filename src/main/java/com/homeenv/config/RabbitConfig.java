package com.homeenv.config;

import com.homeenv.messaging.IndexingResponsesReceiver;
import com.homeenv.repository.ImageClassificationRepository;
import com.homeenv.repository.ImageRepository;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfig {

    @Autowired
    private ApplicationProperties applicationProperties;

    @Autowired
    private ConnectionFactory connectionFactory;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private ImageClassificationRepository imageClassificationRepository;

    @Bean(name = "indexingRequestsQueue")
    Queue indexingRequestsQueue() {
        return new Queue(applicationProperties.getRabbit().getQueueIndexingRequests(), true);
    }

    @Bean(name = "indexingResponsesQueue")
    Queue indexingResponsesQueue() {
        return new Queue(applicationProperties.getRabbit().getQueueIndexingResponses(), true);
    }

//    @Bean
//    TopicExchange exchange() {
//        return new TopicExchange(applicationProperties.getRabbit().getExchangeName());
//    }
//
//    @Bean
//    Binding binding(Queue queue, TopicExchange exchange) {
//        return BindingBuilder.bind(queue).to(exchange).with(messagingProperties.getQueueOutName());
//    }

    @Bean
    public RabbitTemplate rabbitTemplate() {

        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        return rabbitTemplate;
    }

    @Bean
    SimpleMessageListenerContainer container(ConnectionFactory connectionFactory,
                                             MessageListenerAdapter listenerAdapter) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(applicationProperties.getRabbit().getQueueIndexingResponses());
        container.setMessageListener(listenerAdapter);
        return container;
    }

    @Bean
    IndexingResponsesReceiver receiver(RabbitTemplate rabbitTemplate) {
        return new IndexingResponsesReceiver(rabbitTemplate, applicationProperties, imageRepository, imageClassificationRepository);
    }

    @Bean
    MessageListenerAdapter listenerAdapter(IndexingResponsesReceiver receiver) {
        return new MessageListenerAdapter(receiver, "consumeMessage");
    }
}
