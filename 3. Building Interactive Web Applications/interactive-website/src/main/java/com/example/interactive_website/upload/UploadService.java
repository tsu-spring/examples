package com.example.interactive_website.upload;

import com.example.interactive_website.image.ImageService;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class UploadService {

    private final ImageService imageService;

    @SneakyThrows
    public void saveImage(MultipartFile file, LocalDate date) {
        imageService.save(file, date);
    }
}
