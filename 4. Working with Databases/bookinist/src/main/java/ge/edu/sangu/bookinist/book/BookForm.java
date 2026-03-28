package ge.edu.sangu.bookinist.book;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class BookForm {

    @NotBlank(message = "Title is required")
    private String title;

    @NotNull(message = "Author is required")
    private Long authorId;

    @NotNull(message = "Published year is required")
    private Integer publishedYear;

    @NotBlank(message = "ISBN is required")
    private String isbn;

    @DecimalMin(value = "0.0", message = "Rating must be at least 0")
    @DecimalMax(value = "5.0", message = "Rating must be at most 5")
    private Double rating;

    @NotEmpty(message = "Select at least one genre")
    private List<Long> genreIds;

    private MultipartFile coverImage;
}
