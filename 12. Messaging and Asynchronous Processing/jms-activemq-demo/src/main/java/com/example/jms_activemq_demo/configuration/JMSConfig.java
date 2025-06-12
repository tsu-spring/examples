package com.example.jms_activemq_demo.configuration;

import jakarta.jms.ConnectionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageType;

@Configuration
@EnableJms // For topic listener factories
public class JMSConfig {

    @Bean
    public MappingJackson2MessageConverter jacksonJmsMessageConverter() {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setTargetType(MessageType.TEXT); // Sends it as plain JSON string
        converter.setTypeIdPropertyName("_type");  // Adds _type property for receiver
        return converter;
    }

    @Bean(name = "topicJmsTemplate")
    public JmsTemplate topicJmsTemplate(ConnectionFactory connectionFactory) {
        JmsTemplate template = new JmsTemplate(connectionFactory);
        template.setPubSubDomain(true); // Topic mode
        template.setMessageConverter(jacksonJmsMessageConverter()); // Explicitly pass JSON converter!
        return template;
    }

    @Bean
    public DefaultJmsListenerContainerFactory topicListenerFactory(ConnectionFactory connectionFactory) {
        DefaultJmsListenerContainerFactory factory = new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setPubSubDomain(true); // Enables Topic mode
        factory.setMessageConverter(jacksonJmsMessageConverter()); // Explicitly pass JSON converter!
        return factory;
    }
}
