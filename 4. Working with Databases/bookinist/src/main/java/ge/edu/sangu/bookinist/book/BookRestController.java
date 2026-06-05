package ge.edu.sangu.bookinist.book.rest;

import ge.edu.sangu.bookinist.book.Book;
import ge.edu.sangu.bookinist.book.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class BookRestController {

    private final BookService service;

    @GetMapping("/books")
    public List<Book> getAllBooks() {
        return service.getAllBooks();
    }

    @GetMapping("/books/{id}")
    public Book getBookById(@PathVariable long id) {
        return service.getBookById(id);
    }
}
