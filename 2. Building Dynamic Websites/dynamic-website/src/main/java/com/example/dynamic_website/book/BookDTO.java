package com.example.dynamic_website.book;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.Set;

@Data
@NoArgsConstructor
public class BookDTO implements Comparable<BookDTO> {

    private int id;
    private String title;
    private String description;
    private int publicationYear;
    private String isbn;
    private String coverImg;
    private Set<Integer> authors; // ID of authors

    @Override
    public int compareTo(BookDTO o) {
        return Integer.compare(id, o.id);
    }

    public static BookDTO fromBook(Book book) {
        BookDTO bookDTO = new BookDTO();
        BeanUtils.copyProperties(book, bookDTO);
        return bookDTO;
    }
}
