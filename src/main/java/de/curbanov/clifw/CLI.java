package de.curbanov.clifw;

import de.curbanov.clifw.argument.Arg;
import de.curbanov.clifw.command.Cmd;
import de.curbanov.clifw.option.Opt;
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

    private final Args input;
    private final Schema schema;

    private final List<Opt> opts;
    private final List<Arg> args;
    private final List<Cmd> cmds;

    private Result result = null;

    CLI(CliBuilderBase builder) {
        this.input = builder.getInput();
        this.schema = builder.getSchema();
        this.opts = builder.getOpts();
        this.args = builder.getArgs();
        this.cmds = builder.getCmds();
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

            ArgsParser parser = new ArgsParser(this.input, this.schema, this.opts, this.args);
            result = parser.parse();
        }
    }

    private void runShell() {
        throw new UnsupportedOperationException("not yet implemented");
    }
}
