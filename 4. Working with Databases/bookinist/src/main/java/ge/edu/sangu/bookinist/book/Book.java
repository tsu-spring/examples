package ge.edu.sangu.bookinist.book;

import ge.edu.sangu.bookinist.genre.Genre;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "BOOKS")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name="TITLE", nullable = false, unique = true)
    private String title;

    @Column(name = "AUTHOR", nullable = false)
    private String author;

    @ManyToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.REFRESH})
    @JoinTable(
            name = "BOOK_GENRES",
            joinColumns = @JoinColumn(name = "BOOK_ID"),
            inverseJoinColumns = @JoinColumn(name = "GENRE_ID")
    )
    private Set<Genre> genres = new HashSet<>();

    @Column(name="PUBLISHED_YEAR", nullable = false)
    private Integer publishedYear;

    @Column(name="ISBN", nullable = false, unique = true)
    private String isbn;

    @Column(name = "COVER_URL_MEDIUM")
    private String coverUrlMedium;

    @Column(name = "COVER_URL_LARGE")
    private String coverUrlLarge;

    @Lob
    @Column(name = "COVER_IMAGE")
    private byte[] coverImage;

    @Column(name = "COVER_IMAGE_TYPE")
    private String coverImageType;

    @Column(name = "RATING")
    private Double rating;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
