package dev.renankrz.library.commands;

import java.util.HashMap;
import java.util.Map;

import org.springframework.shell.command.annotation.Command;
import org.springframework.shell.command.annotation.Option;
import org.springframework.shell.component.flow.ComponentFlow;
import org.springframework.shell.component.flow.ComponentFlow.ComponentFlowResult;

import dev.renankrz.library.services.LibraryService;

@Command(group = "Library Commands")
public class LibraryCommands {

    private final LibraryService libraryService;
    private final ComponentFlow.Builder componentFlowBuilder;

    public LibraryCommands(LibraryService libraryService, ComponentFlow.Builder componentFlowBuilder) {
        this.libraryService = libraryService;
        this.componentFlowBuilder = componentFlowBuilder;
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
            @Option(shortNames = 'i') String id,
            @Option(shortNames = 'n') String name,
            @Option(shortNames = 'y') String year,
            @Option(shortNames = 'e') String edition,
            @Option(shortNames = 'a') String authors,
            @Option(shortNames = 't') String tags) {

        return libraryService.getBooks(id, name, year, edition, authors, tags);
    }

    @Command(command = "authors", description = "List authors.")
    public String authors(
            @Option(shortNames = 'n') String name) {
        return libraryService.getAuthors(name);
    }

    @Command(command = "tags", description = "List tags.")
    public String tags(
            @Option(shortNames = 'n') String name) {
        return libraryService.getTags(name);
    }

    @Command(command = "rm", description = "Remove stuff.")
    public String rm(
            @Option(shortNames = 'a') String authorId,
            @Option(shortNames = 'b') String bookId,
            @Option(shortNames = 't') String tagId) {

        if (authorId != null) {
            return libraryService.removeAuthor(authorId);
        } else if (bookId != null) {
            return libraryService.removeBook(bookId);
        } else if (tagId != null) {
            return libraryService.removeTag(tagId);
        }

        return "An id must be provided.";
    }

    @Command(command = "fix", description = "Fix stuff.")
    public String fix() {

        Map<String, String> entityTypes = new HashMap<>();
        entityTypes.put("Author", "author-id");
        entityTypes.put("Book", "book-id");
        entityTypes.put("Tag", "tag-id");

        Map<String, String> bookColumns = new HashMap<>();
        bookColumns.put("Name", "book-new-name");
        bookColumns.put("Year", "book-new-year");
        bookColumns.put("Edition", "book-new-edition");
        bookColumns.put("Authors", "book-new-authors");
        bookColumns.put("Tags", "book-new-tags");

        ComponentFlow flow = componentFlowBuilder.clone().reset()
                .withSingleItemSelector("entity-type")
                .name("Entity type")
                .selectItems(entityTypes)
                .next(ctx -> ctx.getResultItem().get().getItem())
                .and()
                .withStringInput("author-id")
                .name("Author id")
                .next(ctx -> "author-new-name")
                .and()
                .withStringInput("author-new-name")
                .name("New name")
                .next(ctx -> null)
                .and()
                .withStringInput("book-id")
                .name("Book id")
                .next(ctx -> "book-columns")
                .and()
                .withSingleItemSelector("book-columns")
                .name("Column")
                .selectItems(bookColumns)
                .next(ctx -> ctx.getResultItem().get().getItem())
                .and()
                .withStringInput("book-new-name")
                .name("New name")
                .next(ctx -> null)
                .and()
                .withStringInput("book-new-year")
                .name("New year")
                .next(ctx -> null)
                .and()
                .withStringInput("book-new-edition")
                .name("New edition")
                .next(ctx -> null)
                .and()
                .withStringInput("book-new-authors")
                .name("New authors")
                .next(ctx -> null)
                .and()
                .withStringInput("book-new-tags")
                .name("New tags")
                .next(ctx -> null)
                .and()
                .withStringInput("tag-id")
                .name("Tag id")
                .next(ctx -> "tag-new-name")
                .and()
                .withStringInput("tag-new-name")
                .name("New name")
                .next(ctx -> null)
                .and()
                .build();

        ComponentFlowResult result = flow.run();

        if (result.getContext().containsKey("author-id")) {
            return libraryService.fixAuthorName(
                    result.getContext().get("author-id"),
                    result.getContext().get("author-new-name"));
        } else if (result.getContext().containsKey("book-id")) {

            if (result.getContext().containsKey("book-new-name")) {
                return libraryService.fixBookName(
                        result.getContext().get("book-id"),
                        result.getContext().get("book-new-name"));
            } else if (result.getContext().containsKey("book-new-year")) {
                return libraryService.fixBookYear(
                        result.getContext().get("book-id"),
                        result.getContext().get("book-new-year"));
            } else if (result.getContext().containsKey("book-new-edition")) {
                return libraryService.fixBookEdition(
                        result.getContext().get("book-id"),
                        result.getContext().get("book-new-edition"));
            } else if (result.getContext().containsKey("book-new-authors")) {
                return libraryService.fixBookAuthors(
                        result.getContext().get("book-id"),
                        result.getContext().get("book-new-authors"));
            } else if (result.getContext().containsKey("book-new-tags")) {
                return libraryService.fixBookTags(
                        result.getContext().get("book-id"),
                        result.getContext().get("book-new-tags"));
            }

            return "Nothing to fix.";

        } else if (result.getContext().containsKey("tag-id")) {
            return libraryService.fixTagName(
                    result.getContext().get("tag-id"),
                    result.getContext().get("tag-new-name"));
        }

        return "Nothing to fix.";
    }

}
