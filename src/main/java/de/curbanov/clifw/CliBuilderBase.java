package de.curbanov.clifw;

import de.curbanov.clifw.command.Command;
import de.curbanov.clifw.option.Option;

import java.util.ArrayList;
import java.util.List;

public abstract class CliBuilderBase<T extends CliBuilderBase<T>> implements CliBuilder<T> {

    private final Args args;
    private final Schema schema;

    private final List<Option> options;
    private final List<Command> commands;

    CliBuilderBase(Args args, Schema schema) {
        this.args = args;
        this.schema = schema;
        this.options = schema.allowsOptions() ? new ArrayList<Option>() : null;
        this.commands = schema.allowsCommands() ? new ArrayList<Command>() : null;
    }

    Args getArgs() {
        return this.args;
    }

    Schema getSchema() {
        return this.schema;
    }

    List<Option> getOptions() {
        return this.options;
    }

    List<Command> getCommands() {
        return this.commands;
    }

    public T addOption(Option option) {
        if (schema.allowsOptions()) {
            this.options.add(option);
        } else {
            throw new UnsupportedOperationException();
        }

        return (T) this;
    }

    public T addOptions(Option... options) {
        if (schema.allowsOptions()) {
            for (Option opt : options) {
                this.options.add(opt);
            }
        } else {
            throw new UnsupportedOperationException();
        }

        return (T) this;
    }

    public T addCommand(Command command) {
        if (schema.allowsCommands()) {
            this.commands.add(command);
        } else {
            throw new UnsupportedOperationException();
        }

        return (T) this;
    }

    public T addCommands(Command... commands) {
        if (schema.allowsCommands()) {
            for (Command cmd : commands) {
                this.commands.add(cmd);
            }
        } else {
            throw new UnsupportedOperationException();
        }

        return (T) this;
    }

    public CLI build() {
        return new CLI(this);
    }
}
