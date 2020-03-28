package de.curbanov.clifw.argument;

abstract class ArgBuilderBase<T extends ArgBuilderBase<T>> implements ArgBuilder<T> {

    private String placeholder = "";
    private String description = "";
    private Class<?> clazz = null;

    String getPlaceholder() {
        return this.placeholder;
    }

    String getDescription() {
        return this.description;
    }

    Class<?> getClazz() {
        return this.clazz;
    }

    T clazz(Class<?> clazz) {
        if (this.clazz != null) {
            throw new IllegalStateException();
        }

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
            this.clazz = clazz;
        } else {
            throw new IllegalArgumentException();
        }

        return (T) this;
    }

    @Override
    public T placeholder(String placeholder) {
        if (this.placeholder.isEmpty()) {
            this.placeholder = placeholder;
        } else {
            throw new IllegalStateException();
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
    public Arg build() {
        return new Arg(this);
    }
}
