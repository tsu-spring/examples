package ge.edu.sangu.bookinist.author;

import ge.edu.sangu.bookinist.error.AuthorNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository authorRepository;

    public Author getAuthorById(Long id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new AuthorNotFoundException(id));
    }

    public Author getAuthorByName(String name) {
        return authorRepository.findByName(name);
    }

    public List<Author> getAllAuthors() {
        return authorRepository.findAll(Sort.by("name"));
    }

    public Author saveAuthor(Author author) {
        return authorRepository.save(author);
    }
}
