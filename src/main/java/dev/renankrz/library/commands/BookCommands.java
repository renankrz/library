package dev.renankrz.library.commands;

import java.util.List;

import org.springframework.shell.command.annotation.Command;
import org.springframework.shell.command.annotation.Option;

import dev.renankrz.library.model.Book;
import dev.renankrz.library.services.BookService;

@Command(command = "b", group = "Book Commands")
class BookCommands {

    private final BookService service;

    public BookCommands(BookService service) {
        this.service = service;
    }

    @Command(command = "all")
    public String all() {
        List<Book> results = service.findAll();
        return String.join("\n", results.stream()
                .map(b -> String.join(" | ",
                        List.of(
                                b.getName(),
                                String.join(", ", b.getAuthors().stream().map(a -> a.getName()).toList()),
                                String.join(", ", b.getTags().stream().map(t -> t.getName()).toList()))))
                .toList());
    }

    @Command(command = "search")
    public String search(@Option(longNames = "name", shortNames = 'n') String name) {
        List<Book> results = service.findByName(name);
        return String.join("\n", results.stream()
                .map(b -> String.join(" | ",
                        List.of(
                                b.getName(),
                                String.join(", ", b.getAuthors().stream().map(a -> a.getName()).toList()),
                                String.join(", ", b.getTags().stream().map(t -> t.getName()).toList()))))
                .toList());
    }

}
