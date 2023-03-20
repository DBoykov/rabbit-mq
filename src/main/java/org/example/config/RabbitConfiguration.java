package org.example.config;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfiguration {

    /* common queue */
    @Value("${rabbit.simple.queue-name}")
    private String QUEUE_NAME;

    @Value("${rabbit.simple.routing-key}")
    private String ROUTING_KEY;

    @Value("${rabbit.simple.exchanger-name}")
    private String EXCHANGER_NAME;

    @Value("${rabbit.dead-letter-messages.queue-name}")
    private String DLQ_QUEUE_NAME;

    @Value("${rabbit.dead-letter-messages.exchanger-name}")
    private String DLQ_EXCHANGER_NAME;

    @Bean
    public ConnectionFactory connectionFactory() {
        return new CachingConnectionFactory("localhost");
    }

    @Bean
    public AmqpAdmin amqpAdmin() {
        return new RabbitAdmin(connectionFactory());
    }

    @Bean
    public RabbitTemplate rabbitTemplate() {
        return new RabbitTemplate(connectionFactory());
    }

    @Bean
    public Queue queue() {
        return QueueBuilder.durable(QUEUE_NAME)
                .withArgument("x-dead-letter-exchange", DLQ_EXCHANGER_NAME)
                .withArgument("x-dead-letter-routing-key", DLQ_QUEUE_NAME)
                .build();
    }

    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(EXCHANGER_NAME);
    }

    @Bean
    public Binding binding() {
        return BindingBuilder
                .bind(queue())
                .to(exchange())
                .with(ROUTING_KEY);
    }

}
