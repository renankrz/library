package dev.renankrz.library.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.renankrz.library.model.Author;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    List<Author> findAllByOrderByNameAsc();

    List<Author> findByName(String name);

    List<Author> findByNameContainsIgnoreCase(String name);

}
