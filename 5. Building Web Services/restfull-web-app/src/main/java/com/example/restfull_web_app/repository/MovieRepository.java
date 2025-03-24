package com.example.restfull_web_app.repository;

import com.example.restfull_web_app.entity.Movie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(path = "movies")
public interface MovieRepository extends JpaRepository<Movie, Long> {
}
