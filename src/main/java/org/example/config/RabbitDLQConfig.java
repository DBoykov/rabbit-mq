package org.example.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitDLQConfig {


    @Value("${rabbit.dead-letter-messages.queue-name}")
    private String DLQ_QUEUE_NAME;

    @Value("${rabbit.dead-letter-messages.exchanger-name}")
    private String DLQ_EXCHANGER_NAME;


    @Bean
    public Queue deadLetterQueue(){
        return new Queue(DLQ_QUEUE_NAME);
    }

    @Bean
    FanoutExchange deadLetterExchange() {
        return new FanoutExchange(DLQ_EXCHANGER_NAME);
    }

    @Bean
    Binding deadLetterBinding() {
        return BindingBuilder.bind(deadLetterQueue()).to(deadLetterExchange());
    }

}
