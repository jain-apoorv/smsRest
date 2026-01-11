package com.vardh.smsRest.sms.service;

import com.vardh.smsRest.sms.dto.SmsEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

@Service
public class SmsEventPublisher {

    private static final Logger log = LoggerFactory.getLogger(SmsEventPublisher.class);

    private final KafkaTemplate<String, byte[]> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Value("${kafka.topic.sms:sms-events}")
    private String topic;

    public SmsEventPublisher(
            KafkaTemplate<String, byte[]> kafkaTemplate,
            ObjectMapper objectMapper
    ) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void publish(SmsEvent event) {
        try {
            byte[] payload = objectMapper.writeValueAsBytes(event);
            kafkaTemplate.send(topic, event.getPhoneNumber(), payload);
        } catch (Exception e) {
            log.error("Failed to publish SMS event", e);
        }
    }
}
