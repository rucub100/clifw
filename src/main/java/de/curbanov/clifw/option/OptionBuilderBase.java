package de.curbanov.clifw.option;

import java.util.ArrayList;
import java.util.List;

abstract class OptionBuilderBase<T extends OptionBuilderBase<T>> implements OptionBuilder<T> {

    private char shortId = 0;
    private String longId = "";
    private String description = "";
    private boolean required = false;
    private List<Class> args = new ArrayList<>();

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

    List<Class> getArgs() {
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
    public T addArgument(Class clazz) {
        if ((clazz.isPrimitive() && !clazz.equals(void.class)) ||
                clazz.equals(String.class) ||
                clazz.equals(Boolean.class) ||
                clazz.equals(Byte.class) ||
                clazz.equals(Character.class) ||
                clazz.equals(Double.class) ||
                clazz.equals(Float.class) ||
                clazz.equals(Integer.class) ||
                clazz.equals(Long.class) ||
                clazz.equals(Short.class)) {
            this.args.add(clazz);
        } else {
            throw new IllegalArgumentException();
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
    public Option build() {
        if (!this.required || this.args.size() > 0) {
            return new Option(this);
        } else {
            throw new IllegalStateException();
        }
    }
}
