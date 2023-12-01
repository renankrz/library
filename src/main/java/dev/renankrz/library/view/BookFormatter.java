package dev.renankrz.library.view;

import java.util.List;

import dev.renankrz.library.model.Author;
import dev.renankrz.library.model.Book;
import dev.renankrz.library.model.Tag;

public class BookFormatter {

    public static String formatList(List<Book> books) {
        int maxLenId = "Id".length();
        int maxLenName = "Name".length();
        int maxLenYear = "Year".length();
        int maxLenEdition = "Edition".length();
        int maxLenAuthors = "Authors".length();
        int maxLenTags = "Tags".length();

        for (Book b : books) {
            maxLenId = Math.max(maxLenId, String.valueOf(b.getId()).length());
            maxLenName = Math.max(maxLenName, String.valueOf(b.getName()).length());
            maxLenYear = Math.max(maxLenYear, String.valueOf(b.getYear()).length());
            maxLenEdition = Math.max(maxLenEdition, String.valueOf(b.getEdition()).length());

            maxLenAuthors = Math.max(maxLenAuthors,
                    b.getAuthors().stream()
                            .map(Author::getLastName)
                            .mapToInt(String::length)
                            .map(x -> x + 2)
                            .sum() - 2);

            maxLenTags = Math.max(maxLenTags,
                    b.getTags().stream()
                            .map(Tag::getName)
                            .mapToInt(String::length)
                            .map(x -> x + 2)
                            .sum() - 2);
        }

        String result = String.format("%1$-" + maxLenId + "s", "Id") + " | " +
                String.format("%1$-" + maxLenName + "s", "Name") + " | " +
                String.format("%1$-" + maxLenYear + "s", "Year") + " | " +
                String.format("%1$-" + maxLenEdition + "s", "Edition") + " | " +
                String.format("%1$-" + maxLenAuthors + "s", "Authors") + " | " +
                String.format("%1$-" + maxLenTags + "s", "Tags") + "\n" +
                "-".repeat(maxLenId + 1) + "+" +
                "-".repeat(maxLenName + 2) + "+" +
                "-".repeat(maxLenYear + 2) + "+" +
                "-".repeat(maxLenEdition + 2) + "+" +
                "-".repeat(maxLenAuthors + 2) + "+" +
                "-".repeat(maxLenTags + 1) + "\n";

        for (Book b : books) {
            result += String.format("%1$-" + maxLenId + "s", b.getId()) + " | " +
                    String.format("%1$-" + maxLenName + "s", b.getName()) + " | " +
                    String.format("%1$-" + maxLenYear + "s", b.getYear()) + " | " +
                    String.format("%1$-" + maxLenEdition + "s", b.getEdition()) + " | " +
                    String.format("%1$-" + maxLenAuthors + "s",
                            String.join(", ", b.getAuthors().stream().map(a -> a.getLastName()).toList()))
                    + " | " +
                    String.format("%1$-" + maxLenTags + "s",
                            String.join(", ", b.getTags().stream().map(t -> t.getName()).toList()))
                    + "\n";
        }

        return result;
    }

}
