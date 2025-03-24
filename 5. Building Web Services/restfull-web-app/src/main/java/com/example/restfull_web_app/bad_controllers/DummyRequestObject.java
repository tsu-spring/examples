package com.example.restfull_web_app.bad_controllers;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class DummyRequestObject {

    @NotBlank(message = "Text is required")
    @Size(max = 50, message = "Text cannot exceed 50 characters")
    private String text;
}
