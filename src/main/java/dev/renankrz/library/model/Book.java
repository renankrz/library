package dev.renankrz.library.model;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import dev.renankrz.library.Constants;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Entity
public class Book {

    @Id
    @GeneratedValue(generator = Constants.ID_GENERATOR)
    private Long id;

    @NotBlank
    @Column(nullable = false)
    private String name;

    @NotNull
    @Column(nullable = false)
    private Integer year;

    private Integer edition;

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(name = "book_author", joinColumns = @JoinColumn(name = "book_id"), inverseJoinColumns = @JoinColumn(name = "author_id"))
    private Set<Author> authors = new HashSet<>();

    @ManyToMany(cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    @JoinTable(name = "book_tag", joinColumns = @JoinColumn(name = "book_id"), inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private Set<Tag> tags = new HashSet<>();

    Book() {
    }

    public Book(String name, Integer year, Integer edition) {
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

    public void setName(String name) {
        this.name = name;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getEdition() {
        return edition;
    }

    public void setEdition(Integer edition) {
        this.edition = edition;
    }

    public Set<Author> getAuthors() {
        return Collections.unmodifiableSet(authors);
    }

    public void addAuthor(Author author) {
        authors.add(author);
    }

    public void removeAuthor(Author author) {
        authors.remove(author);
    }

    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    public void addTag(Tag tag) {
        tags.add(tag);
    }

    public void removeTag(Tag tag) {
        tags.remove(tag);
    }

}
