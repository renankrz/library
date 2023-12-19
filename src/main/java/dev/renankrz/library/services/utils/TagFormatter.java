package dev.renankrz.library.services.utils;

import java.util.List;

import dev.renankrz.library.model.Tag;

public class TagFormatter {

    public static String formatList(List<Tag> tags) {
        int maxLenId = "Id".length();
        int maxLenName = "Name".length();

        for (Tag t : tags) {
            maxLenId = Math.max(maxLenId, String.valueOf(t.getId()).length());
            maxLenName = Math.max(maxLenName, String.valueOf(t.getName()).length());
        }

        String result = String.format("%1$-" + maxLenId + "s", "Id") + " | " +
                String.format("%1$-" + maxLenName + "s", "Name") + "\n" +
                "-".repeat(maxLenId + 1) + "+" +
                "-".repeat(maxLenName + 2) + "\n";

        for (Tag t : tags) {
            result += String.format("%1$-" + maxLenId + "s", t.getId()) + " | " +
                    String.format("%1$-" + maxLenName + "s", t.getName()) + "\n";
        }

        return result;
    }

}
