package org.example.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class DeadLetterHandlerService {

    @RabbitListener(queues = {"${rabbit.simple.queue-name}"})
    public void processFailedMessages(Message message) {
        //TODO: SAVE JSON TO DataBase
        log.info("Received failed message: {}", message.toString());
    }

}
