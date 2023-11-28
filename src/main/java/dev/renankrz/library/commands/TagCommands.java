package dev.renankrz.library.commands;

import java.util.List;

import org.springframework.shell.command.annotation.Command;
import org.springframework.shell.command.annotation.Option;

import dev.renankrz.library.model.Tag;
import dev.renankrz.library.services.TagService;

@Command(command = "t", group = "Tag Commands")
class TagCommands {

    private final TagService service;

    public TagCommands(TagService service) {
        this.service = service;
    }

    @Command(command = "all")
    public String getAll() {
        List<Tag> results = service.findAll();
        return String.join("\n", results.stream()
                .map(t -> t.getName())
                .toList());
    }

    @Command(command = "search")
    public String search(@Option(longNames = "name", shortNames = 'n') String name) {
        List<Tag> results = service.findByName(name);
        return String.join("\n", results.stream()
                .map(t -> t.getName())
                .toList());
    }

}
