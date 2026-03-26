package ge.edu.sangu.bookinist.error;

public class BookNotFoundException extends RuntimeException {

    public BookNotFoundException(Long id) {
        super("Book with ID " + id + " was not found.");
    }
}
