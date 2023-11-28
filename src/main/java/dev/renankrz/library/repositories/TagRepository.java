package dev.renankrz.library.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import dev.renankrz.library.model.Tag;

public interface TagRepository extends JpaRepository<Tag, Long> {
    List<Tag> findByNameContainsIgnoreCase(String name);
}
