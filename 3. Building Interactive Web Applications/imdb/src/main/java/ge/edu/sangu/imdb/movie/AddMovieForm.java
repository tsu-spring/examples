package ge.edu.sangu.imdb.movie;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AddMovieForm {

    @NotBlank(message = "Title is required")
    private String title;

    @NotBlank(message = "Description is required")
    private String description;

    @NotNull(message = "Release year is required")
    private Integer releaseYear;

    @NotBlank(message = "Genre is required")
    private String genre;

    @NotBlank(message = "Director is required")
    private String director;

    @NotBlank(message = "Actors are required")
    private String actors;

    @NotNull(message = "Poster is required")
    private MultipartFile poster;
}
