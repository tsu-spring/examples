package com.example.dynamic_website.book;

import com.example.dynamic_website.author.AuthorDTO;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@NoArgsConstructor
public class BookWithAuthorsDTO implements Comparable<BookWithAuthorsDTO> {

    private int id;
    private String title;
    private String description;
    private int publicationYear;
    private String isbn;
    private String coverImg;
    private Set<AuthorDTO> authors;

    @Override
    public int compareTo(BookWithAuthorsDTO o) {
        return Integer.compare(id, o.id);
    }
}
