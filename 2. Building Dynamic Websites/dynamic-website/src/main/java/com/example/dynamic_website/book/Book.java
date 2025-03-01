package com.example.dynamic_website.book;

import lombok.Data;

import java.util.Set;

@Data
public class Book implements Comparable<Book> {

    private final int id;
    private final String title;
    private final String description;
    private final int publicationYear;
    private final String isbn;
    private final String coverImg;
    private final Set<Integer> authors; // ID of authors

    @Override
    public int compareTo(Book o) {
        return Integer.compare(id, o.id);
    }
}
