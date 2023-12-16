package dev.renankrz.library.services.utils;

import java.util.HashSet;
import java.util.Set;

public class StringUtils {

    public static boolean containsAllStrings(Set<String> completeStrings, Set<String> partialStrings) {
        for (String partial : partialStrings) {
            if (completeStrings.stream().noneMatch(complete -> complete.contains(partial))) {
                return false;
            }
        }
        return true;
    }

    public static Set<String> tokenizeInput(String input) {

        String[] pieces = input.toLowerCase().split(" ");

        Set<String> tokens = new HashSet<>();
        String buffer = "";
        boolean quotesOpen = false;

        for (String piece : pieces) {
            if (!quotesOpen) {
                if (!piece.startsWith("\"")) {
                    tokens.add(piece);
                } else {
                    if (piece.endsWith("\"")) {
                        tokens.add(piece.substring(1, piece.length() - 1));
                    } else {
                        quotesOpen = true;
                        buffer += piece.substring(1);
                    }
                }
            } else {
                if (!piece.endsWith("\"")) {
                    buffer += " " + piece;
                } else {
                    quotesOpen = false;
                    buffer += " " + piece.substring(0, piece.length() - 1);
                    tokens.add(buffer);
                    buffer = "";
                }
            }
        }

        return tokens;

    }

}
