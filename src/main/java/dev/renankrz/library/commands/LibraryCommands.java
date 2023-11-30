package dev.renankrz.library.commands;

import java.util.List;

import org.springframework.shell.command.annotation.Command;
import org.springframework.shell.command.annotation.Option;

import dev.renankrz.library.model.Book;
import dev.renankrz.library.services.BookService;
import dev.renankrz.library.view.BookFormatter;

@Command(group = "Library Commands")
class LibraryCommands {

    private final BookService service;

    public LibraryCommands(BookService service) {
        this.service = service;
    }

    @Command(command = "find", description = "Find books.")
    public String find(
            @Option(shortNames = 'n') String name,
            @Option(shortNames = 'a') String author,
            @Option(shortNames = 't') String tag) {

        List<Book> results;

        if (name != null) {
            results = service.findByName(name);
        } else if (author != null) {
            results = service.findByAuthor(author);
        } else if (tag != null) {
            results = service.findByTag(tag);
        } else {
            results = service.findAll();
        }

        return BookFormatter.formatList(results);
    }

}
