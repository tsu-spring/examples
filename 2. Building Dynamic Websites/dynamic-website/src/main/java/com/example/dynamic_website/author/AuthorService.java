package com.example.dynamic_website.author;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorService {

    private final AuthorRepository repository;

    public Set<AuthorDTO> getAllAuthors() {
        return repository.getAll().parallelStream()
                .map(AuthorDTO::fromAuthor)
                .collect(Collectors.toSet());
    }

    public AuthorDTO getAuthorById(Integer id) {
        return AuthorDTO.fromAuthor(repository.getById(id));
    }
}
