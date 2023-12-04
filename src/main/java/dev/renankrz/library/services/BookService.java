package dev.renankrz.library.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Example;
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

    public List<Book> findAllByExample(Example<Book> example) {
        return repo.findAll(example);
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

    public Optional<Book> findById(Long id) {
        return repo.findById(id);
    }

    public void save(Book b) {
        repo.save(b);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

}
