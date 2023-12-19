package dev.renankrz.library.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import dev.renankrz.library.model.Book;
import jakarta.transaction.Transactional;

public interface BookRepository extends JpaRepository<Book, Long> {

    Book findFirstById(Long id);

    List<Book> findByNameContainsIgnoreCase(String term);

    @Query("SELECT DISTINCT b FROM Book b JOIN FETCH b.authors a JOIN FETCH b.authors WHERE a.id = :id")
    List<Book> findByAuthorId(@Param("id") Long id);

    @Query("SELECT DISTINCT b FROM Book b JOIN FETCH b.tags t JOIN FETCH b.tags WHERE t.id = :id")
    List<Book> findByTagId(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query("UPDATE Book b SET b.edition = :edition WHERE b.id = :id")
    int updateEdition(@Param("id") Long id, @Param("edition") Integer edition);

    @Modifying
    @Transactional
    @Query("UPDATE Book b SET b.name = :name WHERE b.id = :id")
    int updateName(@Param("id") Long id, @Param("name") String name);

    @Modifying
    @Transactional
    @Query("UPDATE Book b SET b.year = :year WHERE b.id = :id")
    int updateYear(@Param("id") Long id, @Param("year") Integer year);

}
