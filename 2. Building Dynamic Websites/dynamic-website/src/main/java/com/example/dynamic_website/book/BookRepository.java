package com.example.dynamic_website.book;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.TreeSet;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class BookRepository {

    // Imagine that this is a book table from database :)
    private Set<Book> books = new TreeSet<>();

    public void save(Book book) {
        books.add(book);
    }

    public Set<Book> getAll() {
        return books;
    }

    public Set<Book> getBooksByPage(int pageNumber, int pageSize) {
        if (pageNumber < 1 || pageSize < 1) {
            throw new IllegalArgumentException("Page number and page size must be greater than 0.");
        }

        return books.stream()
                .skip((pageNumber - 1) * pageSize)
                .limit(pageSize)
                .collect(Collectors.toSet());
    }

    public Book getById(Integer id) {
        return books.stream()
                .filter(i -> i.getId() == id)
                .findFirst().orElseThrow(
                        () -> new IllegalArgumentException(String.format("No book with id: %d", id))
                );
    }

    public int getCount() {
        return books.size();
    }
}
