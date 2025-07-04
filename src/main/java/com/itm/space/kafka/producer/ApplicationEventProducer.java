package com.itm.space.kafka.producer;

import com.itm.space.kafka.event.ApplicationEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Component;

import java.util.concurrent.CompletableFuture;

@Component
@Slf4j
@RequiredArgsConstructor
public class ApplicationEventProducer implements EventProducer<ApplicationEvent> {

    private final KafkaTemplate<String, ApplicationEvent> kafkaTemplate;

    @Value("${spring.kafka.topic.application-events}")
    private String topic;

    @Override
    public void produce(ApplicationEvent event) {

        if (event == null) {
            throw new IllegalArgumentException("Application event is empty");
        }

        if (event.data() == null) {
            throw new IllegalArgumentException("Data (json) is empty");
        }

        CompletableFuture<SendResult<String, ApplicationEvent>> send = kafkaTemplate.send(topic, event);

        send.whenComplete((result, exception) -> {
            if (exception != null) {
                log.error("Error sending message: {}", exception.getMessage(), exception);
            } else {
                log.info("Message sent successfully: {}", result.getRecordMetadata().timestamp());
            }
        });
    }
}
