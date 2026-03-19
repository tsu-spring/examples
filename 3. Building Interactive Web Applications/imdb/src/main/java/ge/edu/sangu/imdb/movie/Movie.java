package ge.edu.sangu.imdb.movie;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Movie {
    private Long id;
    private String title;
    private String description;
    private int releaseYear;
    private Double rating;
    private String genre;
    private String director;
    private Set<String> actors;
    private String posterName;
}
