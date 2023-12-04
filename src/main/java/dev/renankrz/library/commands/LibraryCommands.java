package dev.renankrz.library.commands;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.ExampleMatcher.StringMatcher;
import org.springframework.shell.command.annotation.Command;
import org.springframework.shell.command.annotation.Option;

import dev.renankrz.library.model.Author;
import dev.renankrz.library.model.Book;
import dev.renankrz.library.model.Tag;
import dev.renankrz.library.services.AuthorService;
import dev.renankrz.library.services.BookService;
import dev.renankrz.library.services.TagService;
import dev.renankrz.library.view.BookFormatter;
import jakarta.transaction.Transactional;

@Command(group = "Library Commands")
class LibraryCommands {

    private final AuthorService authorService;
    private final BookService bookService;
    private final TagService tagService;

    public LibraryCommands(AuthorService authorService, BookService bookService, TagService tagService) {
        this.authorService = authorService;
        this.bookService = bookService;
        this.tagService = tagService;
    }

    @Transactional
    @Command(command = "add", description = "Add books.")
    public String add(
            @Option(shortNames = 'n', required = true) String name,
            @Option(shortNames = 'y', required = true) String year,
            @Option(shortNames = 'e') String edition,
            @Option(shortNames = 'a') String authors,
            @Option(shortNames = 't', required = true) String tags) {

        Book b = new Book(name, Integer.valueOf(year), Integer.valueOf(edition));

        List<String> authorsNamesList = List.of(authors.split(","));
        List<Author> authorsList = authorsNamesList.stream().map(authorName -> {
            List<Author> existingAuthor = authorService.findByName(authorName);
            return existingAuthor.isEmpty() ? new Author(authorName) : existingAuthor.get(0);
        }).toList();

        for (Author a : authorsList) {
            b.addAuthor(a);
            a.addBook(b);
        }

        List<String> tagsNamesList = List.of(tags.split(","));
        List<Tag> tagsList = tagsNamesList.stream().map(tagName -> {
            List<Tag> existingTag = tagService.findByName(tagName);
            return existingTag.isEmpty() ? new Tag(tagName) : existingTag.get(0);
        }).toList();

        for (Tag t : tagsList) {
            b.addTag(t);
            t.addBook(b);
        }

        bookService.save(b);

        return "Book added.";
    }

    @Command(command = "find", description = "Find books.")
    public String find(
            @Option(shortNames = 'n') String name,
            @Option(shortNames = 'y') String year,
            @Option(shortNames = 'e') String edition,
            @Option(shortNames = 'a') String authors,
            @Option(shortNames = 't') String tags) {

        Book probe = new Book(
                name,
                year != null ? Integer.parseInt(year) : null,
                edition != null ? Integer.parseInt(edition) : null);

        Example<Book> example = Example.of(probe,
                ExampleMatcher.matchingAll().withIgnoreCase().withStringMatcher(StringMatcher.CONTAINING));
        List<Book> results = bookService.findAllByExample(example);

        if (authors == null && tags == null) {
            return BookFormatter.formatList(results);
        }

        List<Book> resultsFilteredByAuthor = new ArrayList<>(results);

        if (authors != null) {
            Set<String> inputAuthorsNames = List.of(authors.split(",")).stream().collect(Collectors.toSet());

            for (Book r : results) {
                if (!r.getAuthors().stream().map(a -> a.getName()).collect(Collectors.toSet())
                        .containsAll(inputAuthorsNames)) {
                    resultsFilteredByAuthor.remove(r);
                }
            }
        }

        List<Book> resultsFilteredByTag = new ArrayList<>(resultsFilteredByAuthor);

        if (tags != null) {
            Set<String> inputTagsNames = List.of(tags.split(",")).stream().collect(Collectors.toSet());

            for (Book r : resultsFilteredByAuthor) {
                if (!r.getTags().stream().map(t -> t.getName()).collect(Collectors.toSet())
                        .containsAll(inputTagsNames)) {
                    resultsFilteredByTag.remove(r);
                }
            }
        }

        return BookFormatter.formatList(resultsFilteredByTag);
    }

    @Command(command = "rm", description = "Remove books.")
    public String remove(
            @Option(shortNames = 'i', required = true) String id) {
        bookService.delete(Long.parseLong(id));

        return "Book removed.";
    }

}
