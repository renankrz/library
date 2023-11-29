package dev.renankrz.library.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import dev.renankrz.library.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByNameContainsIgnoreCase(String term);

    @Query("SELECT b FROM Book b JOIN FETCH b.authors a WHERE a.name LIKE %:term%")
    List<Book> findByAuthor(@Param("term") String term);
}
