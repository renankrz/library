package dev.renankrz.library.services;

import java.util.List;

import org.springframework.stereotype.Service;

import dev.renankrz.library.model.Book;
import dev.renankrz.library.repositories.BookRepository;

@Service
public class BookService {

    private final BookRepository repo;

    public BookService(BookRepository repo) {
        this.repo = repo;
    }

    public List<Book> findAll() {
        return repo.findAll();
    }

    public List<Book> findByName(String name) {
        return repo.findByNameContainsIgnoreCase(name);
    }
}
