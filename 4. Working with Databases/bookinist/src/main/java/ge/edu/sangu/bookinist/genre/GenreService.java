package ge.edu.sangu.bookinist.genre;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class GenreService {

    private final GenreRepository genreRepository;

    public List<Genre> getAllGenres() {
        return genreRepository.findAll(Sort.by("name"));
    }

    public Set<Genre> getGenresByIds(List<Long> ids) {
        return new HashSet<>(genreRepository.findAllById(ids));
    }
}
