package com.example.restfull_web_app.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "PEOPLE")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "FIRST_NAME", nullable = false)
    private String firstName;

    @Lob
    @Column(name = "LAST_NAME", nullable = false)
    private String lastName;

    @ManyToMany
    @JoinTable(
            name = "DIRECTOR_MOVIE",
            joinColumns = @JoinColumn(name = "DIRECTOR_ID"),
            inverseJoinColumns = @JoinColumn(name = "MOVIE_ID")
    )
    private Set<Movie> directorOfMovies = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "WRITER_MOVIE",
            joinColumns = @JoinColumn(name = "WRITER_ID"),
            inverseJoinColumns = @JoinColumn(name = "MOVIE_ID")
    )
    private Set<Movie> writerOfMovies = new HashSet<>();

    @ManyToMany
    @JoinTable(
            name = "STAR_MOVIE",
            joinColumns = @JoinColumn(name = "STAR_ID"),
            inverseJoinColumns = @JoinColumn(name = "MOVIE_ID")
    )
    private Set<Movie> starOfMovies = new HashSet<>();
}
