package de.curbanov.clifw.option;

import de.curbanov.clifw.argument.Arg;

public interface OptBuilder<T extends OptBuilder<? extends T>> {

    T shortId(char id);
    T longId(String id);
    T addArg(Arg arg);
    T addArgs(Arg... args);
    T description(String description);
    T required();
    Opt build();
}
