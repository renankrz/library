package dev.renankrz.library.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.renankrz.library.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {
    List<Book> findByNameContainsIgnoreCase(String name);
}
