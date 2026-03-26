package ge.edu.sangu.bookinist;

import ge.edu.sangu.bookinist.book.Book;
import ge.edu.sangu.bookinist.book.BookRepository;
import ge.edu.sangu.bookinist.genre.Genre;
import ge.edu.sangu.bookinist.genre.GenreRepository;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class FakeDataFiller implements CommandLineRunner {

    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;
    private final ObjectMapper objectMapper;

    @Override
    public void run(String... args) throws Exception {
        FakeData fakeData = objectMapper.readValue(Path.of("fake-data.json"), new TypeReference<>() {});

        genreRepository.saveAll(fakeData.getGenres());

        fakeData.getBooks().forEach(book -> {
            Set<Genre> genres = book.getGenres()
                    .stream()
                    .map(genreRepository::findByName)
                    .collect(Collectors.toSet());

            Book newBook = new Book();
            newBook.setTitle(book.getTitle());
            newBook.setAuthor(book.getAuthor());
            newBook.setPublishedYear(book.getPublishedYear());
            newBook.setIsbn(book.getIsbn());
            newBook.setCoverUrlMedium(book.getCoverUrlMedium());
            newBook.setCoverUrlLarge(book.getCoverUrlLarge());
            newBook.setRating(book.getRating());
            newBook.setGenres(genres);
            newBook.setCreatedAt(LocalDateTime.now());
            bookRepository.save(newBook);
        });
    }
}

@Getter
@Setter
@NoArgsConstructor
class FakeData {
    private List<Genre> genres;
    private List<JsonBook> books;
}

@Getter
@Setter
@NoArgsConstructor
class JsonBook {
    private String title;
    private String author;
    private Integer publishedYear;
    private String isbn;
    private String coverUrlMedium;
    private String coverUrlLarge;
    private Double rating;
    private List<String> genres;
}
