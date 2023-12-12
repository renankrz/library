package dev.renankrz.library.commands;

import org.springframework.shell.command.annotation.Command;
import org.springframework.shell.command.annotation.Option;

import dev.renankrz.library.services.LibraryService;

@Command(group = "Library Commands")
public class LibraryCommands {

    private final LibraryService libraryService;

    public LibraryCommands(LibraryService libraryService) {
        this.libraryService = libraryService;
    }

    @Command(command = "add", description = "Add a book.")
    public String add(
            @Option(shortNames = 'n', required = true) String name,
            @Option(shortNames = 'y', required = true) String year,
            @Option(shortNames = 'e') String edition,
            @Option(shortNames = 'a') String authors,
            @Option(shortNames = 't', required = true) String tags) {

        return libraryService.addBook(name, year, edition, authors, tags);
    }

    @Command(command = "books", description = "List books.")
    public String books(
            @Option(shortNames = 'n') String name,
            @Option(shortNames = 'y') String year,
            @Option(shortNames = 'e') String edition,
            @Option(shortNames = 'a') String authors,
            @Option(shortNames = 't') String tags) {

        return libraryService.getBooks(name, year, edition, authors, tags);
    }

    @Command(command = "authors", description = "List authors.")
    public String authors() {
        return libraryService.getAuthors();
    }

    @Command(command = "tags", description = "List tags.")
    public String tags() {
        return libraryService.getTags();
    }

    @Command(command = "rm", description = "Remove stuff.")
    public String rm(
            @Option(shortNames = 'a') String authorId,
            @Option(shortNames = 'b') String bookId,
            @Option(shortNames = 't') String tagId) {

        if (authorId != null) {
            return libraryService.removeAuthor(Long.parseLong(authorId));
        } else if (bookId != null) {
            return libraryService.removeBook(Long.parseLong(bookId));
        } else if (tagId != null) {
            return libraryService.removeTag(Long.parseLong(tagId));
        }

        return "An id must be provided.";
    }

}
