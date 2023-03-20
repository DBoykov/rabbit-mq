package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.dto.MessageDto;
import org.example.service.ProducerService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/rabbitmq")
@RequiredArgsConstructor
public class SimpleController {

    private final ProducerService producerService;

    @PostMapping("/send")
    public ResponseEntity<HttpStatus> send(@RequestBody MessageDto messageDto) {

        producerService.send(messageDto);
        return ResponseEntity.ok(HttpStatus.ACCEPTED);
    }

}
