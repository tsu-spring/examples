package com.example.jms_activemq_demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Email { // Must be serializable only if default Java serialization is used!
    private String to;
    private String subject;
    private String body;
}
