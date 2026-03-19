package ge.edu.sangu.imdb.poster;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Set;
import java.util.UUID;

@Slf4j
@Service
public class PosterService {

    private static final Set<String> ALLOWED_EXTENSIONS = Set.of("jpg", "jpeg", "png", "gif", "webp");

    @Value("${imdb.posters.folder}")
    private Path postersFolder;

    @PostConstruct
    void init() throws IOException {
        postersFolder = postersFolder.resolve("poster");
        if (Files.exists(postersFolder)) {
            log.debug("Posters folder already exists at {}", postersFolder);
        } else {
            Files.createDirectories(postersFolder);
            log.debug("Created posters folder at {}", postersFolder);
        }
    }

    public Path save(MultipartFile file) throws IOException {
        String extension = getExtension(file);
        if (!ALLOWED_EXTENSIONS.contains(extension.toLowerCase())) {
            throw new IllegalArgumentException(
                    "Invalid image type: " + extension + ". Allowed: " + ALLOWED_EXTENSIONS);
        }
        String uniqueFilename = UUID.randomUUID() + "." + extension;
        Path imagePath = postersFolder.resolve(uniqueFilename);
        return Files.write(imagePath, file.getBytes());
    }

    public void deletePosterWithName(String posterName) {
        try {
            Files.deleteIfExists(postersFolder.resolve(posterName));
        } catch (IOException e) {
            log.error("Error deleting poster with name {}", posterName, e);
        }
    }

    private String getExtension(MultipartFile file) {
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || !originalFilename.contains(".")) {
            throw new IllegalArgumentException("File has no extension");
        }
        return originalFilename.substring(originalFilename.lastIndexOf(".") + 1);
    }
}
