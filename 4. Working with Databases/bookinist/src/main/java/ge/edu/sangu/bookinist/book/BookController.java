package ge.edu.sangu.bookinist.book;

import ge.edu.sangu.bookinist.genre.GenreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.time.LocalDateTime;

@Controller
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;
    private final GenreService genreService;

    @GetMapping({"/", "/index", "/home"})
    public String index(@SortDefault(sort = "publishedYear", direction = Sort.Direction.DESC) Pageable pageable,
                        Model model) {
        Page<Book> bookPage = bookService.getBooks(pageable);
        model.addAttribute("bookPage", bookPage);
        return "index";
    }

    @PostMapping("/search")
    public String search(@RequestParam String query, RedirectAttributes redirectAttributes) {
        redirectAttributes.addAttribute("query", query);
        return "redirect:/search";
    }

    @GetMapping("/search")
    public String searchResults(@RequestParam String query,
                                @SortDefault(sort = "publishedYear", direction = Sort.Direction.DESC) Pageable pageable,
                                Model model) {
        Page<Book> bookPage = bookService.searchByTitle(query, pageable);
        model.addAttribute("bookPage", bookPage);
        model.addAttribute("query", query);
        return "search";
    }

    @GetMapping("/book/new")
    public String showAddBookForm(Model model) {
        model.addAttribute("bookForm", new BookForm());
        model.addAttribute("genres", genreService.getAllGenres());
        return "add-book";
    }

    @PostMapping("/book/new")
    public String addBook(@Valid @ModelAttribute("bookForm") BookForm bookForm,
                          BindingResult bindingResult,
                          Model model) throws IOException {
        if (bindingResult.hasErrors()) {
            model.addAttribute("genres", genreService.getAllGenres());
            return "add-book";
        }

        Book book = new Book();
        book.setTitle(bookForm.getTitle());
        book.setAuthor(bookForm.getAuthor());
        book.setPublishedYear(bookForm.getPublishedYear());
        book.setIsbn(bookForm.getIsbn());
        book.setRating(bookForm.getRating());
        book.setGenres(genreService.getGenresByIds(bookForm.getGenreIds()));
        book.setCreatedAt(LocalDateTime.now());

        MultipartFile coverImage = bookForm.getCoverImage();
        if (coverImage != null && !coverImage.isEmpty()) {
            book.setCoverImage(coverImage.getBytes());
            book.setCoverImageType(coverImage.getContentType());
        }

        Book savedBook = bookService.saveBook(book);
        return "redirect:/book/" + savedBook.getId();
    }

    @GetMapping("/book/{id}")
    public String book(@PathVariable Long id, Model model) {
        model.addAttribute("book", bookService.getBookById(id));
        return "book";
    }
}
