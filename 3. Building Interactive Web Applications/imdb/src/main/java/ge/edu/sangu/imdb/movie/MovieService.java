package ge.edu.sangu.imdb.movie;

import ge.edu.sangu.imdb.poster.PosterService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MovieService {

    private final MovieRepository repository;
    private final PosterService posterService;

    public List<Movie> getAllMovies() {
        return repository.findAll();
    }

    public Movie getMovieById(long id) {
        return repository.findById(id)
                .orElseThrow(() -> new MovieNotFoundException(
                        String.format("Movie with id '%d' not found", id)
                ));
    }

    public Movie saveMovie(AddMovieForm form) {
        String posterName;
        try {
            Path posterPath = posterService.save(form.getPoster());
            posterName = posterPath.getFileName().toString();
        } catch (IOException e) {
            throw new RuntimeException("Failed to save poster", e);
        }
        try {
            return repository.save(map(form, posterName));
        } catch (Exception e) {
            posterService.deletePosterWithName(posterName);
            throw new RuntimeException("Failed to save movie", e);
        }
    }

    private Movie map(AddMovieForm form, String posterName) {
        Movie movie = new Movie();
        movie.setTitle(form.getTitle());
        movie.setDescription(form.getDescription());
        movie.setReleaseYear(form.getReleaseYear());
        movie.setGenre(form.getGenre());
        movie.setDirector(form.getDirector());
        movie.setPosterName(posterName);

        Set<String> actors = Arrays.stream(form.getActors().split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toCollection(LinkedHashSet::new));
        movie.setActors(actors);

        return movie;
    }
}
