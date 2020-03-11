package de.curbanov.clifw.parsing;

import de.curbanov.clifw.argument.Argument;
import de.curbanov.clifw.command.Command;
import de.curbanov.clifw.option.Option;

import java.util.Collections;
import java.util.List;

public class Result {

    private final List<Option> options;
    private final List<Argument<?>> arguments;
    private final Command command;

    Result(List<Option> options, List<Argument<?>> arguments) {
        this.arguments = arguments;
        this.options = options;
        this.command = null;
    }

    Result(Command command) {
        this.arguments = null;
        this.options = null;
        this.command = command;
    }

    public boolean hasOptions() {
        return this.options != null && this.options.size() > 0;
    }

    public List<Option> getOptions() {
        return Collections.unmodifiableList(this.options);
    }

    public boolean hasArguments() { return this.arguments != null && this.arguments.size() > 0; }

    public List<Argument<?>> getArguments() { return Collections.unmodifiableList(this.arguments); }

    public boolean isCommand() { return this.command != null; }

    public Command getCommand() { return this.command; }
}
