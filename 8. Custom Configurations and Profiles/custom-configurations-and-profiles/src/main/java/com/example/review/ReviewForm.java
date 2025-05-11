package com.example.review;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class ReviewForm {

    @NotNull(message = "Manga ID is required")
    private final Long mangaId;

    @NotBlank(message = "Text cannot be blank")
    @Size(min = 20, max = 350, message = "Text must be at least {min} and at most most {max} characters long")
    private String text;

    @Max(value = 5, message = "Stars must be at most {max} value")
    @Min(value = 1, message = "Stars must be at least {min} value")
    private Integer stars;
}
