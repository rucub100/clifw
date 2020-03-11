package de.curbanov.clifw.option;

import de.curbanov.clifw.argument.Arg;

import java.util.List;

public class Opt {

    private final char optionShort;
    private final String optionLong;
    private final String description;
    private final boolean required;
    private final List<Arg> args;

    Opt(OptBuilderBase builder) {
        this.optionShort = builder.getShortId();
        this.optionLong = builder.getLongId();
        this.description = builder.getDescription();
        this.required = builder.isRequired();
        this.args = builder.getArgs();
    }

    public static DefaultOptBuilder useChar(char opt) {
        return new DefaultOptBuilder().shortId(opt);
    }

    public static DefaultOptBuilder useName(String name) {
        return new DefaultOptBuilder().longId(name);
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

        return this.args.get(index).getClazz();
    }

    @Override
    public String toString() {
        return (this.optionShort == '-') ? "" : "-" + this.optionShort + ", " +
                this.optionLong + "\t" + this.description;
    }
}
