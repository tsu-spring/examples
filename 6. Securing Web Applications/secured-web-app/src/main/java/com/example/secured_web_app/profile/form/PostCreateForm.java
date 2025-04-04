package com.example.secured_web_app.profile.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class PostCreateForm {

    @NotBlank(message = "Title is required")
    @Size(max = 50, message = "Title should exceed 50 characters")
    private String title;

    @NotBlank(message = "Content is required")
    @Size(max = 2000, message = "Content should exceed 2000 characters")
    private String content;
}