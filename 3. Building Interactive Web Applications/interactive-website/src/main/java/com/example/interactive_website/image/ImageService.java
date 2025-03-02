package com.example.interactive_website.image;

import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class ImageService {

    @Getter
    @Value("${image.root-folder}")
    private Path rootFolder;

    private final ImageRepository repository;

    @SneakyThrows
    @PostConstruct
    public void init() {
        // Create root folder if it doesn't exist
        Files.createDirectories(rootFolder);

        // Construct all the existing image entries in the root folder
        try (Stream<Path> stream = Files.walk(rootFolder)) {
            stream.filter(Files::isRegularFile).forEach(this::storeAlreadyExistingFilesInRepository);
        }
    }

    @SneakyThrows
    private void storeAlreadyExistingFilesInRepository(Path path) {
        Path relativePath = rootFolder.relativize(path);
        final var date = LocalDate.of(
                Integer.parseInt(relativePath.getName(0).toString()),
                Integer.parseInt(relativePath.getName(1).toString()),
                Integer.parseInt(relativePath.getName(2).toString())
        );
        save(relativePath, date); // Save only entries (do not create files)
    }

    public List<Image> getImages() {
        return repository.getAll();
    }

    public List<Image> getImagesSortedByDate() {
        return repository.getAllSorted(Comparator.comparing(Image::getDate).reversed());
    }

    public void save(Path relativePath, LocalDate date) {
        repository.save(new Image(relativePath, date));
    }

    @SneakyThrows
    public void save(MultipartFile file, LocalDate date) {
        // Construct relative path for image file
        Path relativePath = Path.of(Integer.toString(date.getYear()))
                .resolve(Integer.toString(date.getMonthValue()))
                .resolve(Integer.toString(date.getDayOfMonth()))
                .resolve(String.format("%s.%s",
                        UUID.randomUUID(), FilenameUtils.getExtension(file.getOriginalFilename())));

        // Resolve full path of the image
        Path fullPath = getRootFolder().resolve(relativePath);

        // Create folders if they don't exist
        Files.createDirectories(fullPath.getParent());

        // Save image to file
        try (InputStream inputStream = file.getInputStream();
             OutputStream outputStream = Files.newOutputStream(fullPath)) {
            IOUtils.copy(inputStream, outputStream);
        }

        // Save image entry
        save(relativePath, date);
    }
}
