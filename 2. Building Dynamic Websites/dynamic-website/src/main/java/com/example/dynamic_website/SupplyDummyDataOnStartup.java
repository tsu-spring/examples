package com.example.dynamic_website;

import com.example.dynamic_website.author.Author;
import com.example.dynamic_website.author.AuthorRepository;
import com.example.dynamic_website.book.Book;
import com.example.dynamic_website.book.BookRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class SupplyDummyDataOnStartup {

    private final ResourceLoader resourceLoader;
    private final ObjectMapper objectMapper;

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    @SneakyThrows
    @EventListener(ApplicationReadyEvent.class)
    public void onApplicationReady() {

        // (1) Process Authors
        Resource authorsResource = resourceLoader.getResource("classpath:authors.json");
        List<JsonNode> authorNodes = objectMapper.readValue(authorsResource.getFile(), new TypeReference<>() {
        });
        for (JsonNode authorNode : authorNodes) {
            int id = authorNode.get("id").asInt();
            String firstName = authorNode.get("firstName").asText();
            String lastName = authorNode.get("lastName").asText();
            String info = authorNode.get("info").asText();
            String photoUrl = authorNode.get("photoUrl").asText();
            Author author = new Author(id, firstName, lastName, info, photoUrl);
            authorRepository.save(author);
        }

        // (2) Process books
        Resource booksResource = resourceLoader.getResource("classpath:books.json");
        List<JsonNode> bookNodes = objectMapper.readValue(booksResource.getFile(), new TypeReference<>() {
        });

        for (JsonNode bookNode : bookNodes) {
            int id = bookNode.get("id").asInt();
            String title = bookNode.get("title").asText();
            String description = bookNode.get("description").asText();
            String coverImg = bookNode.get("coverImg").asText();
            int publicationYear = bookNode.get("publicationYear").asInt();
            String isbn = bookNode.get("isbn").asText();

            Book book = new Book(id, title, description, publicationYear, isbn, coverImg,
                    objectMapper.convertValue(bookNode.get("authors"), Set.class));
            bookRepository.save(book);
        }
    }
}
