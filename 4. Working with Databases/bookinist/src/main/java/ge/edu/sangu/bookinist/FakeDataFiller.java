package ge.edu.sangu.bookinist;

import ge.edu.sangu.bookinist.author.Author;
import ge.edu.sangu.bookinist.author.AuthorRepository;
import ge.edu.sangu.bookinist.book.Book;
import ge.edu.sangu.bookinist.book.BookRepository;
import ge.edu.sangu.bookinist.genre.Genre;
import ge.edu.sangu.bookinist.genre.GenreRepository;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.JsonNode;
import tools.jackson.databind.ObjectMapper;

import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class FakeDataFiller implements CommandLineRunner {

    private final BookRepository bookRepository;
    private final GenreRepository genreRepository;
    private final AuthorRepository authorRepository;
    private final ObjectMapper objectMapper;

    @Override
    public void run(String... args) throws Exception {
        FakeData fakeData = objectMapper.readValue(Path.of("fake-data.json"), new TypeReference<>() {});

        genreRepository.saveAll(fakeData.getGenres());

        RestClient restClient = RestClient.builder()
                .defaultHeader("User-Agent", "Bookinist/1.0 (educational project)")
                .build();

        log.info("Fetching author photos from Wikipedia...");

        fakeData.getAuthors().forEach(jsonAuthor -> {
            Author author = new Author();
            author.setName(jsonAuthor.getName());
            author.setBio(jsonAuthor.getBio());

            if (jsonAuthor.getWikipediaSlug() != null) {
                try {
                    String json = restClient.get()
                            .uri("https://en.wikipedia.org/api/rest_v1/page/summary/{slug}", jsonAuthor.getWikipediaSlug())
                            .retrieve()
                            .body(String.class);
                    JsonNode node = objectMapper.readTree(json);
                    if (node.has("thumbnail")) {
                        author.setPhotoUrl(node.get("thumbnail").get("source").asText());
                    }
                    log.info("Fetched photo for: {}", jsonAuthor.getName());
                } catch (Exception e) {
                    log.warn("Failed to fetch Wikipedia photo for: {}", jsonAuthor.getName());
                }
            }

            authorRepository.save(author);
        });

        log.info("All authors saved. Loading books...");

        fakeData.getBooks().forEach(book -> {
            Set<Genre> genres = book.getGenres()
                    .stream()
                    .map(genreRepository::findByName)
                    .collect(Collectors.toSet());

            Author author = authorRepository.findByName(book.getAuthor());

            Book newBook = new Book();
            newBook.setTitle(book.getTitle());
            newBook.setAuthor(author);
            newBook.setPublishedYear(book.getPublishedYear());
            newBook.setIsbn(book.getIsbn());
            newBook.setCoverUrlMedium(book.getCoverUrlMedium());
            newBook.setCoverUrlLarge(book.getCoverUrlLarge());
            newBook.setRating(book.getRating());
            newBook.setGenres(genres);
            newBook.setCreatedAt(LocalDateTime.now());
            bookRepository.save(newBook);
        });

        log.info("Fake data loaded: {} authors, {} books", fakeData.getAuthors().size(), fakeData.getBooks().size());
    }
}

@Getter
@Setter
@NoArgsConstructor
class FakeData {
    private List<Genre> genres;
    private List<JsonAuthor> authors;
    private List<JsonBook> books;
}

@Getter
@Setter
@NoArgsConstructor
class JsonAuthor {
    private String name;
    private String bio;
    private String wikipediaSlug;
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
