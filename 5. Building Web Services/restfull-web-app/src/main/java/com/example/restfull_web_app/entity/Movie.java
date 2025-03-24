package com.example.restfull_web_app.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "MOVIES")
public class Movie {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "TITLE", nullable = false)
    private String title;

    @Lob
    @Column(name = "DESCRIPTION", nullable = false)
    private String description;

    @Column(name = "RELEASE_YEAR", nullable = false)
    private Integer releaseYear;

    @ManyToMany(mappedBy = "movies")
    private Set<Genre> genres = new HashSet<>();

    @ManyToMany(mappedBy = "directorOfMovies")
    private Set<Person> directors = new HashSet<>();

    @ManyToMany(mappedBy = "writerOfMovies")
    private Set<Person> writers = new HashSet<>();

    @ManyToMany(mappedBy = "starOfMovies")
    private Set<Person> stars = new HashSet<>();
}
