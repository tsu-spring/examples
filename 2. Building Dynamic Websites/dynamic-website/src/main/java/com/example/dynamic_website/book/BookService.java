package com.example.dynamic_website.book;

import com.example.dynamic_website.author.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookService {

    @Value("${book-page.size}")
    private int pageSize;

    private final BookRepository repository;
    private final AuthorService authorService;

    public int getTotalNumberOfPages() {
        return Math.ceilDiv(repository.getCount(), pageSize);
    }

    public Set<BookDTO> getAllBooks() {
        return repository.getAll().parallelStream()
                .map(BookDTO::fromBook)
                .collect(Collectors.toSet());
    }

    public Set<BookWithAuthorsDTO> getAllBooksWithAuthors() {
        return getAllBooks().parallelStream()
                .map(this::fromBookDTO)
                .collect(Collectors.toSet());
    }

    public Set<BookWithAuthorsDTO> getBooksWithAuthorsByPage(int pageNumber) {
        if (pageNumber < 1 || pageSize < 1) {
            throw new IllegalArgumentException("Page number and page size must be greater than 0.");
        }
        return repository.getBooksByPage(pageNumber, pageSize).parallelStream()
                .map(BookDTO::fromBook)
                .map(this::fromBookDTO)
                .collect(Collectors.toSet());
    }

    public BookDTO getBookById(Integer id) {
        return BookDTO.fromBook(repository.getById(id));
    }

    public BookWithAuthorsDTO getBookWithAuthorById(Integer bookId) {
        return fromBookDTO(getBookById(bookId));
    }

    public BookWithAuthorsDTO fromBookDTO(BookDTO bookDTO) {
        var bookWithAuthorsDTO = new BookWithAuthorsDTO();
        BeanUtils.copyProperties(bookDTO, bookWithAuthorsDTO, "authors");
        bookWithAuthorsDTO.setAuthors(
                bookDTO.getAuthors().parallelStream()
                        .map(authorService::getAuthorById)
                        .collect(Collectors.toSet())
        );
        return bookWithAuthorsDTO;
    }
}
