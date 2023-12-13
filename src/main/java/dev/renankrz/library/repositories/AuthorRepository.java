package dev.renankrz.library.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import dev.renankrz.library.model.Author;
import jakarta.transaction.Transactional;

public interface AuthorRepository extends JpaRepository<Author, Long> {

    List<Author> findAllByOrderByNameAsc();

    List<Author> findByName(String name);

    List<Author> findByNameContainsIgnoreCase(String name);

    @Modifying
    @Transactional
    @Query("UPDATE Author a SET a.name = :name WHERE a.id = :id")
    int updateName(@Param("id") Long id, @Param("name") String name);

}
