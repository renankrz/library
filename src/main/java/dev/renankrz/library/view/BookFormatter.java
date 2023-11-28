package dev.renankrz.library.view;

import java.util.List;

import dev.renankrz.library.model.Book;

public class BookFormatter {

    public static String format(Book b) {
        return String.join(" | ",
                List.of(
                        b.getName(),
                        String.join(", ", b.getAuthors().stream().map(a -> a.getName()).toList()),
                        String.join(", ", b.getTags().stream().map(t -> t.getName()).toList())));
    }

    public static String formatList(List<Book> books) {
        return String.join("\n", books.stream()
                .map(b -> BookFormatter.format(b)).toList());
    }

}
