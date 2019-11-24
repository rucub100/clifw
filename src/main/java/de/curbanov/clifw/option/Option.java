package de.curbanov.clifw.option;

import java.util.List;

public class Option {

    private final char optionShort;
    private final String optionLong;
    private final String description;
    private final boolean required;
    private final List<Class> args;

    Option(OptionBuilderBase builder) {
        this.optionShort = builder.getShortId();
        this.optionLong = builder.getLongId();
        this.description = builder.getDescription();
        this.required = builder.isRequired();
        this.args = builder.getArgs();
    }

    public static DefaultOptionBuilder useChar(char opt) {
        return new DefaultOptionBuilder().shortId(opt);
    }

    public static DefaultOptionBuilder useName(String name) {
        return new DefaultOptionBuilder().longId(name);
    }

    public boolean isRequired() {
        return this.required;
    }

    public boolean hasShortName() { return this.optionShort != 0; }

    public boolean hasLongName() { return !this.optionLong.isEmpty(); }

    public String getShortName() {
        return hasShortName() ? String.valueOf(this.optionShort) : "";
    }

    public String getLongName() {
        return this.optionLong;
    }

    public boolean hasArgs() { return this.args.size() > 0; }

    public int getArgsCount() { return this.args.size(); }

    public Class getArgType(int index) {
        if (!hasArgs()) {
            throw new UnsupportedOperationException();
        } else if (index < 0 || index >= this.args.size()) {
            throw new IndexOutOfBoundsException();
        }

        return this.args.get(index);
    }

    @Override
    public String toString() {
        return (this.optionShort == '-') ? "" : "-" + this.optionShort + ", " +
                this.optionLong + "\t" + this.description;
    }
}
