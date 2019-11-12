package de.curbanov.clifw.option;

public interface OptionBuilder<T extends OptionBuilder<? extends T>> {

    T shortId(char id);
    T longId(String id);
    T addArgument(Class clazz);
    T description(String description);
    T required();
    Option build();
}
