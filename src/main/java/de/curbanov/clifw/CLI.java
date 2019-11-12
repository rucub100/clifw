package de.curbanov.clifw;

import de.curbanov.clifw.command.Command;
import de.curbanov.clifw.option.Option;
import de.curbanov.clifw.parsing.ArgsParser;
import de.curbanov.clifw.parsing.Result;

import java.util.List;

/**
 * Main API class.
 *
 * @author Ruslan Curbanov, ruslan.curbanov@uni-duesseldorf.de
 * @version 1.0.0.0
 */
public class CLI {

    private final Args args;
    private final Schema schema;

    private final List<Option> options;
    private final List<Command> commands;

    private Result result = null;

    CLI(CliBuilderBase builder) {
        this.args = builder.getArgs();
        this.schema = builder.getSchema();
        this.options = builder.getOptions();
        this.commands = builder.getCommands();
    }

    public static ArgsCliBuilder setArgs(String[] args) {
        return new ArgsCliBuilder(new Args(args), Schema.OPTIONS);
    }

    public static ArgsCliBuilder setArgs(String[] args, Schema schema) {
        return new ArgsCliBuilder(new Args(args), schema);
    }

    public static ShellCliBuilder useShell() {
        return new ShellCliBuilder(Args.EMPTY);
    }

    public boolean hasResult() {
        return this.result != null;
    }

    public Result getResult() {
        return this.result;
    }

    public void run() {
        if (this.schema.isShell()) {
            runShell();
        } else {
            if (hasResult()) {
                throw new UnsupportedOperationException();
            }

            ArgsParser parser = new ArgsParser();
            result = parser.parse(this.args, this.schema, this.options);
        }
    }

    private void runShell() {
        throw new UnsupportedOperationException("not yet implemented");
    }
}
