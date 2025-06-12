package com.example.jms_activemq_demo.producer;

import com.example.jms_activemq_demo.model.Email;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class EmailSender {

    @Qualifier("topicJmsTemplate")
    private final JmsTemplate jmsTemplate;

    public void sendEmail(Email email) {
        jmsTemplate.convertAndSend("mailbox", email);
        log.debug("Sent <{}>", email);
    }
}
