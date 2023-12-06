package dev.renankrz.library.view;

import java.util.List;

import dev.renankrz.library.model.Author;

public class AuthorFormatter {

    public static String formatList(List<Author> authors) {
        int maxLenId = "Id".length();
        int maxLenName = "Name".length();

        for (Author a : authors) {
            maxLenId = Math.max(maxLenId, String.valueOf(a.getId()).length());
            maxLenName = Math.max(maxLenName, String.valueOf(a.getName()).length());
        }

        String result = String.format("%1$-" + maxLenId + "s", "Id") + " | " +
                String.format("%1$-" + maxLenName + "s", "Name") + "\n" +
                "-".repeat(maxLenId + 1) + "+" +
                "-".repeat(maxLenName + 2) + "\n";

        for (Author a : authors) {
            result += String.format("%1$-" + maxLenId + "s", a.getId()) + " | " +
                    String.format("%1$-" + maxLenName + "s", a.getName()) + "\n";
        }

        return result;
    }

}
