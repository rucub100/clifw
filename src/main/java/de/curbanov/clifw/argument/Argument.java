package de.curbanov.clifw.argument;

public class Argument<T> {

    private final Arg blueprint;
    private final T value;

    public Argument(Arg arg, T value) {
        this.blueprint = arg;
        this.value = value;
    }

    public Arg getBlueprint() {
        return this.blueprint;
    }

    public T getValue() {
        return this.value;
    }
}
