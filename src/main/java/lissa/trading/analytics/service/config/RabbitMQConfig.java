package lissa.trading.analytics.service.config;

import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${integration.rabbit.inbound.tg-bot.queue}")
    private String requestQueue;

    @Value("${integration.rabbit.outbound.tg-bot.pulse.queue}")
    private String pulseResponseQueue;

    @Value("${integration.rabbit.outbound.tg-bot.news.queue}")
    private String newsResponseQueue;

    @Value("${integration.rabbit.inbound.tg-bot.exchange}")
    private String analyticsExchange;

    @Value("${integration.rabbit.inbound.tg-bot.routing-key}")
    private String requestRoutingKey;

    @Value("${integration.rabbit.outbound.tg-bot.pulse.routing-key}")
    private String responsePulseRoutingKey;

    @Value("${integration.rabbit.outbound.tg-bot.news.routing-key}")
    private String responseNewsRoutingKey;

    @Bean
    public TopicExchange AnalyticsTopicExchange() {
        return new TopicExchange(analyticsExchange);
    }

    @Bean
    public Queue requestQueue() {
        return new Queue(requestQueue, true);
    }

    @Bean
    public Queue pulseResponseQueue() {
        return new Queue(pulseResponseQueue, true);
    }

    @Bean
    public Queue newsResponseQueue() {
        return new Queue(newsResponseQueue, true);
    }

    @Bean
    public Binding requestBinding(Queue requestQueue, TopicExchange topicExchange) {
        return BindingBuilder.bind(requestQueue).to(topicExchange).with(requestRoutingKey);
    }

    @Bean
    public Binding pulseResponseBinding(Queue pulseResponseQueue,
                                        TopicExchange topicExchange) {
        return BindingBuilder.bind(pulseResponseQueue).to(topicExchange).with(responsePulseRoutingKey);
    }

    @Bean
    public Binding newResponseBinding(Queue newsResponseQueue,
                                      TopicExchange topicExchange) {
        return BindingBuilder.bind(newsResponseQueue).to(topicExchange).with(responseNewsRoutingKey);
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory,
                                         Jackson2JsonMessageConverter jackson2JsonMessageConverter) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jackson2JsonMessageConverter);
        return rabbitTemplate;
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory,
            Jackson2JsonMessageConverter jackson2JsonMessageConverter) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(jackson2JsonMessageConverter);
        factory.setAcknowledgeMode(AcknowledgeMode.AUTO);
        return factory;
    }
}
