package com.example.dynamic_website.author;

import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.TreeSet;

@Repository
public class AuthorRepository {

    // Imagine that this is an authors table from database ;)
    private Set<Author> authors = new TreeSet<>();

    public void save(Author author) {
        authors.add(author);
    }

    public Set<Author> getAll() {
        return authors;
    }

    public Author getById(Integer id) {
        return authors.stream()
                .filter(i -> i.getId() == id)
                .findFirst().orElseThrow(
                        () -> new IllegalArgumentException(String.format("No author with id: %d", id))
                );
    }
}
