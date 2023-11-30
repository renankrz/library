package dev.renankrz.library.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import dev.renankrz.library.model.Book;

public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByNameContainsIgnoreCase(String term);

    @Query("SELECT DISTINCT b FROM Book b JOIN FETCH b.authors a JOIN FETCH b.authors WHERE a.name LIKE %:term%")
    List<Book> findByAuthor(@Param("term") String term);

    @Query("SELECT DISTINCT b FROM Book b JOIN FETCH b.tags t JOIN FETCH b.tags WHERE t.name LIKE %:term%")
    List<Book> findByTag(@Param("term") String term);

}
