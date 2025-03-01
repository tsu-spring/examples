package com.example.dynamic_website.author;

import lombok.Data;

@Data
public class Author implements Comparable<Author> {

    private final int id;
    private final String firstName;
    private final String lastName;
    private final String info;
    private final String photoUrl;

    @Override
    public int compareTo(Author o) {
        return Integer.compare(id, o.id);
    }
}
