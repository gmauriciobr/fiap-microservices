package br.com.fiap.config.rabbitmq;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class RabbitMQConfig {

    public static final String EXCHANGE_NAME = "fiap";
    public static final String ENVIA_PEDIDO_DIRECT_QUEUE = "enviaPedidoDirectQueue";

    private final ConnectionFactory rabbitConnectionFactory;

    @Bean
    public DirectExchange exchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue enviaPeidoDirectQueue() {
        return QueueBuilder.durable(ENVIA_PEDIDO_DIRECT_QUEUE).build();
    }

    @Bean
    public Binding enviaPeidoDirectQueueBinding(Queue enviaPeidoDirectQueue, DirectExchange exchange) {
        return BindingBuilder.bind(enviaPeidoDirectQueue).to(exchange).withQueueName();
    }

    @Bean
    public RabbitTemplate getRabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate(this.rabbitConnectionFactory);
        template.setMessageConverter(new Jackson2JsonMessageConverter());
        return template;
    }

}
