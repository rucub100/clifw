package de.curbanov.clifw.option;

import de.curbanov.clifw.argument.Arg;

import java.util.ArrayList;
import java.util.List;

abstract class OptBuilderBase<T extends OptBuilderBase<T>> implements OptBuilder<T> {

    private char shortId = 0;
    private String longId = "";
    private String description = "";
    private boolean required = false;
    private List<Arg> args = new ArrayList<>();

    char getShortId() {
        return this.shortId;
    }

    String getLongId() {
        return this.longId;
    }

    String getDescription() {
        return this.description;
    }

    boolean isRequired() {
        return this.required;
    }

    List<Arg> getArgs() {
        return this.args;
    }

    @Override
    public T shortId(char id) {
        if (this.shortId == '\u0000' && Character.isLetterOrDigit(id)) {
            this.shortId = id;
        } else {
            throw new IllegalStateException();
        }

        return (T) this;
    }

    @Override
    public T longId(String id) {
        if (this.longId.isEmpty()) {
            this.longId = id;
        } else {
            throw new IllegalStateException();
        }

        return (T) this;
    }

    @Override
    public T addArg(Arg arg) {
        this.args.add(arg);
        return (T) this;
    }

    @Override
    public T addArgs(Arg... args) {
        for (Arg arg : args) {
            this.args.add(arg);
        }

        return (T) this;
    }

    @Override
    public T description(String description) {
        if (this.description.isEmpty()) {
            this.description = description;
        } else {
            throw new IllegalStateException();
        }

        return (T) this;
    }

    @Override
    public T required() {
        if (!this.required) {
            this.required = true;
        } else {
            throw new IllegalStateException();
        }

        return (T) this;
    }

    @Override
    public Opt build() {
        if (!this.required || this.args.size() > 0) {
            return new Opt(this);
        } else {
            throw new IllegalStateException();
        }
    }
}
