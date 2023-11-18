package dev.renankrz.library.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.renankrz.library.model.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {
    List<Author> findByNameContainsIgnoreCase(String name);
}
