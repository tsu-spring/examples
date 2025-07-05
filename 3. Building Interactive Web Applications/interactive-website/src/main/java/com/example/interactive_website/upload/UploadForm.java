package com.example.interactive_website.upload;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Data
public class UploadForm {
    @NotNull(message = "Image file must be selected")
    private MultipartFile image;

    @NotNull(message = "Date of the image must be specified")
    @PastOrPresent(message = "Date cannot be in the future.")
    private LocalDate date;

    private String info;
}
