package de.curbanov.clifw.argument;

public class Arg {

    private final String placeholder;
    private final String description;
    private final Class<?> clazz;

    Arg(ArgBuilderBase builder) {
        this.placeholder = builder.getPlaceholder();
        this.description = builder.getDescription();
        this.clazz = builder.getClazz();
    }

    public static DefaultArgBuilder of(Class<?> clazz) {
        return new DefaultArgBuilder().clazz(clazz);
    }

    public String getPlaceholder() {
        return this.placeholder;
    }

    public String getDescription() {
        return this.description;
    }

    public Class<?> getClazz() {
        return this.clazz;
    }
}
