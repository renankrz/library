package dev.renankrz.library.model;

import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;

@Entity
public class Book {

    @Id
    @GeneratedValue
    private Long id;

    @NotBlank
    @Max(255)
    private final String name;

    private final Integer year;

    private final Integer edition;

    @ManyToMany
    private Set<Author> authors = new HashSet<>();

    Book() {
        this(null, null, null);
    }

    Book(String name, Integer year, Integer edition) {
        this.name = name;
        this.year = year;
        this.edition = edition;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Integer getYear() {
        return year;
    }

    public Integer getEdition() {
        return edition;
    }

    public Set<Author> getAuthors() {
        return authors;
    }

}
