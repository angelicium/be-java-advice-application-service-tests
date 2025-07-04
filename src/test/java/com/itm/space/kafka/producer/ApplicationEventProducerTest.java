package com.itm.space.kafka.producer;

import com.fasterxml.jackson.databind.node.ObjectNode;
import com.itm.space.BaseIntegrationTest;
import com.itm.space.kafka.event.ApplicationEvent;
import com.itm.space.kafka.event.EventType;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

public class ApplicationEventProducerTest extends BaseIntegrationTest {

    @Autowired
    private ApplicationEventProducer producer;

    @Value("${spring.kafka.topic.application-events}")
    private String topic;

    private ObjectNode data;

    @BeforeEach
    void setData() {
        data = objectMapper.createObjectNode();
        data.put("appId", 1);
        data.put("appValue", "value");
    }

    @Test
    @DisplayName("Тест отправки события в Kafka")
    @SneakyThrows
    void shouldProduceApplicationEvent() {
        ApplicationEvent event = new ApplicationEvent(EventType.CONSULTANT_APPLICATION_CREATED, data);
        byte[] expectEvent = objectMapper.writeValueAsBytes(event);

        producer.produce(event);
        testConsumerService.consumeAndValidate(topic, expectEvent);
    }
}
