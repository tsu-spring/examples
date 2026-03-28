package ge.edu.sangu.bookinist.error;

public class AuthorNotFoundException extends RuntimeException {

    public AuthorNotFoundException(Long id) {
        super("Author with ID " + id + " was not found.");
    }
}
