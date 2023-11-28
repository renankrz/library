package dev.renankrz.library.services;

import java.util.List;

import org.springframework.stereotype.Service;

import dev.renankrz.library.model.Author;
import dev.renankrz.library.repositories.AuthorRepository;

@Service
public class AuthorService {

    private final AuthorRepository repo;

    public AuthorService(AuthorRepository repo) {
        this.repo = repo;
    }

    public List<Author> findAll() {
        return repo.findAll();
    }

    public List<Author> findByName(String name) {
        return repo.findByNameContainsIgnoreCase(name);
    }

}
