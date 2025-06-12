package com.example.jms_activemq_demo.controller.form;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class EmailForm {
    @NotBlank(message = "To is required")
    @Email(message = "To must be a valid email address")
    @Size(max = 100, message = "To cannot exceed 100 characters")
    private String to;

    @NotBlank(message = "Subject is required")
    @Size(max = 100, message = "Subject cannot exceed 100 characters")
    private String subject;

    @NotBlank(message = "Body is required")
    @Size(max = 500, message = "Body cannot exceed 500 characters")
    private String body;
}
