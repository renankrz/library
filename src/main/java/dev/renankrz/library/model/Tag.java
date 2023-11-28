package dev.renankrz.library.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Tag {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    @Max(255)
    private String name;

    @ManyToMany(mappedBy = "tags")
    private Set<Book> books = new HashSet<>();

    Tag() {
    }

    Tag(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Book> getBooks() {
        return Collections.unmodifiableSet(books);
    }

    public void addBook(Book book) {
        books.add(book);
    }

}
