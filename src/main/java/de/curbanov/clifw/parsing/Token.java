package de.curbanov.clifw.parsing;

public class Token {

    private final LexicalCategory name;
    private final String value;

    public Token(LexicalCategory name, String value) {
        this.name = name;
        this.value = value;
    }

    public Token(LexicalCategory name) {
        this.name = name;
        this.value = null;
    }

    public LexicalCategory getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}
