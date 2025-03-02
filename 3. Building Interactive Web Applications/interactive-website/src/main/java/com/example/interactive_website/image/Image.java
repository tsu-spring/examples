package com.example.interactive_website.image;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.nio.file.Path;
import java.time.LocalDate;

@Data
@AllArgsConstructor
public class Image {
    private Path relativePath;
    private LocalDate date;
}
