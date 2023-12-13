package dev.renankrz.library.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import dev.renankrz.library.model.Tag;
import jakarta.transaction.Transactional;

public interface TagRepository extends JpaRepository<Tag, Long> {

    List<Tag> findAllByOrderByNameAsc();

    List<Tag> findByName(String name);

    List<Tag> findByNameContainsIgnoreCase(String name);

    @Modifying
    @Transactional
    @Query("UPDATE Tag t SET t.name = :name WHERE t.id = :id")
    int updateName(@Param("id") Long id, @Param("name") String name);

}
