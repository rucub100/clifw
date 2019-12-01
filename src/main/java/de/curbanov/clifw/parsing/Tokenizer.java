package de.curbanov.clifw.parsing;

import de.curbanov.clifw.Args;

import java.util.ArrayList;
import java.util.List;

class Tokenizer {

    public static List<Token> lexicalAnalysis(Args args) {
        List<Token> tokens = new ArrayList<>();

        for (String arg : args) {
            tokens.addAll(nextToken(arg, 0));
        }

        return tokens;
    }

    private static List<Token> nextToken(String term, final int d) {
        if (term.startsWith("-")) {
            if (term.startsWith("--")) {
                if (term.contentEquals("--")) {
                    // args separator
                    return new ArrayList<>(List.of(new Token(LexicalCategory.SEPARATOR, term)));
                } else {
                    // long option
                    List<Token> t = new ArrayList(
                            List.of(new Token(LexicalCategory.OPERATOR, term.substring(0, 2))));
                    t.addAll(nextToken(term.substring(2), d + 1));
                    return t;
                }
            } else {
                // short option
                List<Token> t = new ArrayList<>(
                        List.of(new Token(LexicalCategory.OPERATOR, term.substring(0, 1))));
                t.addAll(nextToken(term.substring(1), d + 1));
                return t;
            }
        } else {
            if (term.contains("=")) {
                if (d <= 0) {
                    throw new IllegalArgumentException();
                }

                String[] terms = term.split("=");

                if (terms.length != 2) {
                    throw new IllegalArgumentException();
                }

                return new ArrayList<>(
                        List.of(
                                new Token(LexicalCategory.IDENTIFIER, terms[0]),
                                new Token(LexicalCategory.SEPARATOR, "="),
                                new Token(LexicalCategory.LITERAL, terms[1])));
            } else {
                LexicalCategory cat = (d > 0) ? LexicalCategory.IDENTIFIER : LexicalCategory.LITERAL;
                return new ArrayList<>(List.of(new Token(cat, term)));
            }
        }
    }
}
