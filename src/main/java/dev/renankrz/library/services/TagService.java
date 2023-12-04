package dev.renankrz.library.services;

import java.util.List;

import org.springframework.stereotype.Service;

import dev.renankrz.library.model.Tag;
import dev.renankrz.library.repositories.TagRepository;

@Service
public class TagService {

    private final TagRepository repo;

    public TagService(TagRepository repo) {
        this.repo = repo;
    }

    public List<Tag> findAll() {
        return repo.findAll();
    }

    public List<Tag> findByName(String name) {
        return repo.findByNameContainsIgnoreCase(name);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

}
