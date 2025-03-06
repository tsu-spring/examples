package com.example.website_with_database.topic;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class TopicForm {

    @NotBlank(message = "Title is required")
    @Size(max = 100, message = "Title cannot exceed 100 characters")
    private String title;

    @NotBlank(message = "Text is required")
    @Size(max = 500, message = "Text cannot exceed 500 characters")
    private String text;
}
