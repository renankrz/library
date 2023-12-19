package dev.renankrz.library.services.utils;

import java.util.List;

import dev.renankrz.library.model.Author;
import dev.renankrz.library.model.Book;
import dev.renankrz.library.model.Tag;

public class BookFormatter {

    private static final int MAX_LEN_NAME = 40;
    private static final int MAX_LEN_AUTHORS = 20;
    private static final int MAX_LEN_TAGS = 60;

    private static String truncate(String text, int maxLen) {
        if (text.length() <= maxLen) {
            return text;
        } else {
            int lastRemainingSpace = text.substring(0, maxLen - 4).lastIndexOf(" ");

            return text.substring(0, lastRemainingSpace + 2) + "...";
        }
    }

    public static String formatOne(Book book) {

        StringBuilder buff = new StringBuilder();

        buff.append("Id: ");
        buff.append(book.getId());
        buff.append("\n");

        buff.append("Name: ");
        buff.append(book.getName());
        buff.append("\n");

        buff.append("Year: ");
        buff.append(book.getYear());
        buff.append("\n");

        buff.append("Edition: ");
        buff.append(book.getEdition());
        buff.append("\n");

        buff.append("Authors: ");
        buff.append(String.join(", ", book.getAuthors().stream().map(a -> a.getName()).toList()));
        buff.append("\n");

        buff.append("Tags: ");
        buff.append(String.join(", ", book.getTags().stream().map(t -> t.getName()).toList()));
        buff.append("\n");

        return buff.toString();
    }

    public static String formatList(List<Book> books) {
        int maxLenId = "Id".length();
        int maxLenName = "Name".length();
        int maxLenYear = "Year".length();
        int maxLenEdition = "Ed.".length();
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

        maxLenName = Math.min(maxLenName, MAX_LEN_NAME);
        maxLenAuthors = Math.min(maxLenAuthors, MAX_LEN_AUTHORS);
        maxLenTags = Math.min(maxLenTags, MAX_LEN_TAGS);

        String result = String.format("%1$-" + maxLenId + "s", "Id") + " | " +
                String.format("%1$-" + maxLenName + "s", "Name") + " | " +
                String.format("%1$-" + maxLenYear + "s", "Year") + " | " +
                String.format("%1$-" + maxLenEdition + "s", "Ed.") + " | " +
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
                    String.format("%1$-" + maxLenName + "s",
                            truncate(b.getName(), MAX_LEN_NAME))
                    + " | " +
                    String.format("%1$-" + maxLenYear + "s", b.getYear()) + " | " +
                    String.format("%1$-" + maxLenEdition + "s", b.getEdition()) + " | " +
                    String.format("%1$-" + maxLenAuthors + "s",
                            truncate(
                                    String.join(", ", b.getAuthors().stream().map(a -> a.getLastName()).toList()),
                                    MAX_LEN_AUTHORS))
                    + " | " +
                    String.format("%1$-" + maxLenTags + "s",
                            truncate(
                                    String.join(", ", b.getTags().stream().map(t -> t.getName()).toList()),
                                    MAX_LEN_TAGS))
                    + "\n";
        }

        return result;
    }

    public static String generateFilename(Book b) {
        Long id = b.getId();
        String name = b.getName();

        return name.toUpperCase().replace(" ", "-").replace(",", "") + "_" + id;
    }

}
