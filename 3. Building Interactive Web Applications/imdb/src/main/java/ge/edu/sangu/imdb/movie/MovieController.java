package ge.edu.sangu.imdb.movie;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.LinkedHashMap;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class MovieController {

    private final MovieService movieService;

    @GetMapping({"/", "/index", "/home"})
    public String index(Model model) {
        model.addAttribute("movies", movieService.getAllMovies());
        return "index";
    }

    @GetMapping("/movie/{id}")
    public String movie(@PathVariable long id, Model model) {
        model.addAttribute("movie", movieService.getMovieById(id));
        return "movie";
    }

    @GetMapping("/add-movie")
    public String addMovieForm(Model model) {
        model.addAttribute("movie", new AddMovieForm());
        return "add-movie";
    }

    @PostMapping("/add-movie")
    public String addMovie(@Valid @ModelAttribute("movie") AddMovieForm movie,
                           BindingResult bindingResult,
                           RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "add-movie";
        }
        Movie savedMovie = movieService.saveMovie(movie);
        redirectAttributes.addFlashAttribute("savedMovie", savedMovie);
        return "redirect:/add-movie";
    }

    @ModelAttribute("genres")
    public Map<String, String> populateGenres() {
        Map<String, String> genres = new LinkedHashMap<>();
        genres.put("action", "Action");
        genres.put("comedy", "Comedy");
        genres.put("drama", "Drama");
        genres.put("horror", "Horror");
        genres.put("sci-fi", "Sci-Fi");
        genres.put("romance", "Romance");
        return genres;
    }
}
