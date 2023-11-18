package dev.renankrz.library.commands;

import java.util.List;

import org.springframework.shell.command.annotation.Command;
import org.springframework.shell.command.annotation.Option;

import dev.renankrz.library.model.Book;
import dev.renankrz.library.services.BookService;

@Command(command = "b")
class BookCommands {

    private final BookService service;

    public BookCommands(BookService service) {
        this.service = service;
    }

    @Command(command = "all")
    public String getAll() {
        List<Book> results = service.findAll();
        return String.join("\n", results.stream()
                .map(a -> a.getName())
                .toList());
    }

    @Command(command = "search")
    public String search(@Option(longNames = "name", shortNames = 'n') String name) {
        List<Book> results = service.findByName(name);
        return String.join("\n", results.stream()
                .map(a -> a.getName())
                .toList());
    }
}
