package ge.edu.sangu.bookinist.book;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequiredArgsConstructor
public class ImageController {

    private final BookService bookService;

    @GetMapping("/image/cover/{bookId}")
    public ResponseEntity<byte[]> getCoverImage(@PathVariable Long bookId) {
        Book book = bookService.getBookById(bookId);

        if (book.getCoverImage() == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(book.getCoverImageType()));
        return new ResponseEntity<>(book.getCoverImage(), headers, HttpStatus.OK);
    }
}
