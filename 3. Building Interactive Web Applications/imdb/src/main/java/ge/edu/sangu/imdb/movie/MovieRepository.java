package ge.edu.sangu.imdb.movie;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class MovieRepository {

    private final AtomicLong currentId = new AtomicLong(1L);
    private final List<Movie> movies = new ArrayList<>();

    public synchronized List<Movie> findAll() {
        return new ArrayList<>(movies);
    }

    public synchronized Optional<Movie> findById(long id) {
        return movies.stream()
                .filter(movie -> movie.getId() == id)
                .findFirst();
    }

    public synchronized Movie save(Movie movie) {
        if (movie.getId() == null) {
            movie.setId(currentId.getAndIncrement());
        }
        movies.add(movie);
        return movie;
    }

    public synchronized void deleteById(long id) {
        movies.removeIf(movie -> movie.getId() == id);
    }
}
