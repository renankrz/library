package dev.renankrz.library.commands;

import java.util.List;

import org.springframework.shell.command.annotation.Command;
import org.springframework.shell.command.annotation.Option;

import dev.renankrz.library.model.Author;
import dev.renankrz.library.services.AuthorService;

@Command(command = "a", group = "Author Commands")
class AuthorCommands {

    private final AuthorService service;

    public AuthorCommands(AuthorService service) {
        this.service = service;
    }

    @Command(command = "all")
    public String getAll() {
        List<Author> results = service.findAll();
        return String.join("\n", results.stream()
                .map(a -> a.getName())
                .toList());
    }

    @Command(command = "search")
    public String search(@Option(longNames = "name", shortNames = 'n') String name) {
        List<Author> results = service.findByName(name);
        return String.join("\n", results.stream()
                .map(a -> a.getName())
                .toList());
    }

}
