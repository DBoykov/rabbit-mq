package org.example.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import lombok.RequiredArgsConstructor;
import org.example.dto.MessageDto;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ProducerService {

    @Value("${rabbit.simple.queue-name}")
    private String QUEUE_NAME;

    @Value("${rabbit.simple.routing-key}")
    private String ROUTING_KEY;

    @Value("${rabbit.simple.exchanger-name}")
    private String EXCHANGER_NAME;


    private final RabbitTemplate rabbitTemplate;

    public void send(MessageDto messageDto) {
        try {

            String json = toJson(messageDto);
            Message message = toMessage(json);

            rabbitTemplate.convertAndSend(EXCHANGER_NAME, ROUTING_KEY, message);

        } catch (JsonProcessingException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private Message toMessage(String json) {
        return MessageBuilder
                .withBody(json.getBytes())
                .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                .build();
    }

    private String toJson(MessageDto messageDto) throws JsonProcessingException {
        ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
        return ow.writeValueAsString(messageDto);
    }

}
