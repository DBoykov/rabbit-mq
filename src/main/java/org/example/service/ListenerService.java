package org.example.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.dto.MessageDto;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
@RequiredArgsConstructor
public class ListenerService {


    //@RabbitListener(queues = {"${rabbit.simple.queue-name}"})
    private void listen(Message message) {
        try {
            log.info(toMessageDto(message).toString());
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    private MessageDto toMessageDto(Message message) throws IOException {
        return new ObjectMapper().readerFor(MessageDto.class).readValue(message.getBody());
    }

}
