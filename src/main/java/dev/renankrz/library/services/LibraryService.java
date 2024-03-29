package dev.renankrz.library.services;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import dev.renankrz.library.model.Author;
import dev.renankrz.library.model.Book;
import dev.renankrz.library.model.Tag;
import dev.renankrz.library.repositories.AuthorRepository;
import dev.renankrz.library.repositories.BookRepository;
import dev.renankrz.library.repositories.TagRepository;
import dev.renankrz.library.services.utils.AuthorFormatter;
import dev.renankrz.library.services.utils.BookFormatter;
import dev.renankrz.library.services.utils.StringUtils;
import dev.renankrz.library.services.utils.TagFormatter;
import jakarta.transaction.Transactional;

@Service
public class LibraryService {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;
    private final TagRepository tagRepository;

    public LibraryService(
            AuthorRepository authorRepository,
            BookRepository bookRepository,
            TagRepository tagRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
        this.tagRepository = tagRepository;
    }

    @Transactional
    public String addBook(String name, String year, String edition, String authors, String tags) {

        Book b = new Book(
                name.toLowerCase(),
                Integer.valueOf(year),
                edition != null ? Integer.valueOf(edition) : null);

        if (authors != null) {
            List<String> authorsNamesList = List.of(authors.toLowerCase().split(","));

            List<Author> authorsList = authorsNamesList.stream().map(authorName -> {
                if (authorRepository.existsByName(authorName)) {
                    return authorRepository.findFirstByName(authorName);
                }
                return new Author(authorName);
            }).toList();

            for (Author a : authorsList) {
                b.addAuthor(a);
                a.addBook(b);
            }
        }

        List<String> tagsNamesList = List.of(tags.toLowerCase().split(","));

        List<Tag> tagsList = tagsNamesList.stream().map(tagName -> {
            if (tagRepository.existsByName(tagName)) {
                return tagRepository.findFirstByName(tagName);
            }
            return new Tag(tagName);
        }).toList();

        for (Tag t : tagsList) {
            b.addTag(t);
            t.addBook(b);
        }

        bookRepository.save(b);

        return BookFormatter.generateFilename(b);

    }

    public String fixAuthorName(String strId, String newName) {

        Long id = Long.parseLong(strId);

        if (!authorRepository.existsById(id)) {
            return "No author matches the id " + id + ".";
        }

        authorRepository.updateName(id, newName);

        return "Author name updated.";

    }

    @Transactional
    public String fixBookAuthors(String strId, String strNewAuthors) {

        Long id = Long.parseLong(strId);

        if (!bookRepository.existsById(id)) {
            return "No book matches the id " + id + ".";
        }

        Book book = bookRepository.findFirstById(id);

        for (Author author : new HashSet<>(book.getAuthors())) {
            book.removeAuthor(author);
            author.removeBook(book);
        }

        Set<String> authorsNames = StringUtils.tokenizeInput(strNewAuthors);

        Set<Author> authors = authorsNames.stream().map(authorName -> {
            if (authorRepository.existsByName(authorName)) {
                return authorRepository.findFirstByName(authorName);
            }
            return new Author(authorName);
        }).collect(Collectors.toSet());

        for (Author a : authors) {
            book.addAuthor(a);
            a.addBook(book);
        }

        return "Book authors updated.";

    }

    public String fixBookEdition(String strId, String strNewEdition) {

        Long id = Long.parseLong(strId);

        if (!bookRepository.existsById(id)) {
            return "No book matches the id " + id + ".";
        }

        /*
         * TO-DO:
         * Accept null when they fix ComponentContext#get.
         * For now, accepting 0 for null is just a workaround.
         */
        Integer newEdition = Integer.parseInt(strNewEdition);

        if (newEdition == 0) {
            bookRepository.updateEdition(id, null);
        } else {
            bookRepository.updateEdition(id, newEdition);
        }

        return "Book edition updated.";

    }

    public String fixBookName(String strId, String newName) {

        Long id = Long.parseLong(strId);

        if (!bookRepository.existsById(id)) {
            return "No book matches the id " + id + ".";
        }

        bookRepository.updateName(id, newName);

        return "Book name updated.";

    }

    @Transactional
    public String fixBookTags(String strId, String strNewTags) {

        Long id = Long.parseLong(strId);

        if (!bookRepository.existsById(id)) {
            return "No book matches the id " + id + ".";
        }

        Book book = bookRepository.findFirstById(id);

        for (Tag tag : new HashSet<>(book.getTags())) {
            book.removeTag(tag);
            tag.removeBook(book);
        }

        Set<String> tagsNames = StringUtils.tokenizeInput(strNewTags);

        Set<Tag> tags = tagsNames.stream().map(tagName -> {
            if (tagRepository.existsByName(tagName)) {
                return tagRepository.findFirstByName(tagName);
            }
            return new Tag(tagName);
        }).collect(Collectors.toSet());

        for (Tag t : tags) {
            book.addTag(t);
            t.addBook(book);
        }

        return "Book tags updated.";

    }

    public String fixBookYear(String strId, String strNewYear) {

        Long id = Long.parseLong(strId);

        if (!bookRepository.existsById(id)) {
            return "No book matches the id " + id + ".";
        }

        Integer newYear = Integer.parseInt(strNewYear);
        bookRepository.updateYear(id, newYear);

        return "Book year updated.";

    }

    public String fixTagName(String strId, String newName) {

        Long id = Long.parseLong(strId);

        if (!tagRepository.existsById(id)) {
            return "No tag matches the id " + id + ".";
        }

        tagRepository.updateName(id, newName);

        return "Tag name updated.";

    }

    public String getAuthors(String name) {

        List<Author> authors;

        if (name != null) {
            authors = authorRepository.findByNameContainsIgnoreCase(name);
        } else {
            authors = authorRepository.findAllByOrderByNameAsc();
        }

        return AuthorFormatter.formatList(authors);

    }

    public String getBooks(String strId, String name, String year, String edition, String authors, String tags) {

        if (strId != null) {
            Long id = Long.parseLong(strId);

            if (!bookRepository.existsById(id)) {
                return "No book matches the id " + id + ".";
            }

            Book book = bookRepository.findFirstById(id);

            return BookFormatter.formatOne(book);
        }

        Book probe = new Book(
                name,
                year != null ? Integer.parseInt(year) : null,
                edition != null ? Integer.parseInt(edition) : null);

        Example<Book> example = Example.of(probe,
                ExampleMatcher.matchingAll().withIgnoreCase().withStringMatcher(StringMatcher.CONTAINING));

        Sort sortCriteria = Sort.by("year").ascending();
        sortCriteria = sortCriteria.and(Sort.by("name").ascending());

        List<Book> results = bookRepository.findAll(example, sortCriteria);

        if (authors == null && tags == null) {
            return BookFormatter.formatList(results);
        }

        List<Book> resultsFilteredByAuthor = new ArrayList<>(results);

        if (authors != null) {
            Set<String> inputAuthorsNames = List.of(authors.split(",")).stream().collect(Collectors.toSet());

            for (Book r : results) {
                Set<String> authorsNames = r.getAuthors().stream().map(a -> a.getName()).collect(Collectors.toSet());

                if (!StringUtils.containsAllStrings(authorsNames, inputAuthorsNames)) {
                    resultsFilteredByAuthor.remove(r);
                }
            }
        }

        List<Book> resultsFilteredByTag = new ArrayList<>(resultsFilteredByAuthor);

        if (tags != null) {
            Set<String> inputTagsNames = List.of(tags.split(",")).stream().collect(Collectors.toSet());

            for (Book r : resultsFilteredByAuthor) {
                Set<String> tagsNames = r.getTags().stream().map(t -> t.getName()).collect(Collectors.toSet());

                if (!StringUtils.containsAllStrings(tagsNames, inputTagsNames)) {
                    resultsFilteredByTag.remove(r);
                }
            }
        }

        return BookFormatter.formatList(resultsFilteredByTag);

    }

    public String getTags(String name) {

        List<Tag> tags;

        if (name != null) {
            tags = tagRepository.findByNameContainsIgnoreCase(name);
        } else {
            tags = tagRepository.findAllByOrderByNameAsc();
        }

        return TagFormatter.formatList(tags);

    }

    public String removeAuthor(String strId) {

        Long id = Long.parseLong(strId);

        if (!authorRepository.existsById(id)) {
            return "No author matches the id " + id + ".";
        }

        List<Book> books = bookRepository.findByAuthorId(id);

        if (!books.isEmpty()) {
            String message = "Can\'t remove because the author is still referenced by the following books:\n\n";

            return message + BookFormatter.formatList(books);
        }

        authorRepository.deleteById(id);

        return "The author has been removed.";

    }

    public String removeBook(String strId) {

        Long id = Long.parseLong(strId);

        if (!bookRepository.existsById(id)) {
            return "No book matches the id " + id + ".";
        }

        bookRepository.deleteById(id);

        return "The book has been removed.";

    }

    public String removeTag(String strId) {

        Long id = Long.parseLong(strId);

        if (!tagRepository.existsById(id)) {
            return "No tag matches the id " + id + ".";
        }

        List<Book> books = bookRepository.findByTagId(id);

        if (!books.isEmpty()) {
            String message = "Can\'t remove because the tag is still referenced by the following books:\n\n";

            return message + BookFormatter.formatList(books);
        }

        tagRepository.deleteById(id);

        return "The tag has been removed.";

    }

}
