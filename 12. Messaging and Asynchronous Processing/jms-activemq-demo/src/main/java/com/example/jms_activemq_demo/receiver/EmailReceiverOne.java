package com.example.jms_activemq_demo.receiver;

import com.example.jms_activemq_demo.model.Email;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class EmailReceiverOne {

    @JmsListener(destination = "mailbox", containerFactory = "topicListenerFactory")
    public void receiveEmail(Email email) {
        log.debug("[Receiver 1]: Received <{}>", email);
    }
}
