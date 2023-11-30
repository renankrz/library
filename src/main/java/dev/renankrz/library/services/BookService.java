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

    public List<Book> findByName(String term) {
        return repo.findByNameContainsIgnoreCase(term);
    }

    public List<Book> findByAuthor(String term) {
        return repo.findByAuthor(term);
    }

    public List<Book> findByTag(String term) {
        return repo.findByTag(term);
    }

}
