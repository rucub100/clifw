package de.curbanov.clifw.argument;

public interface ArgBuilder<T extends ArgBuilder<? extends T>> {

    T placeholder(String placeholder);
    T description(String description);
    Arg build();
}
