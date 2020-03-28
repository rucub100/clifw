package de.curbanov.clifw.command;

import de.curbanov.clifw.argument.Argument;
import de.curbanov.clifw.option.Option;

import java.util.Collections;
import java.util.List;

public class Command {

    private final Cmd blueprint;
    private final List<Option> options;
    private final List<Argument<?>> arguments;

    public Command(Cmd cmd, List<Option> options, List<Argument<?>> arguments) {
        this.blueprint = cmd;
        this.options = options;
        this.arguments = arguments;
    }

    public Command(Cmd cmd) {
        this(cmd, null, null);
    }

    public Cmd getBlueprint() {
        return this.blueprint;
    }

    public boolean hasOptions() { return this.options != null && this.options.size() > 0; }

    public List<Option> getOptions() {
        return Collections.unmodifiableList(this.options);
    }

    public boolean hasArguments() { return this.arguments != null && this.arguments.size() > 0; }

    public List<Argument<?>> getArguments() {
        return Collections.unmodifiableList(this.arguments);
    }
}
