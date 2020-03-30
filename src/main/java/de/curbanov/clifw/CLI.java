package de.curbanov.clifw;

import de.curbanov.clifw.argument.Arg;
import de.curbanov.clifw.command.Cmd;
import de.curbanov.clifw.command.Command;
import de.curbanov.clifw.option.Opt;
import de.curbanov.clifw.parsing.ArgsParser;
import de.curbanov.clifw.parsing.Result;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.Scanner;
import java.util.function.Consumer;

/**
 * Main API class.
 *
 * @author Ruslan Curbanov, ruslan.curbanov@uni-duesseldorf.de
 */
public class CLI {

    private final Args input;
    private final Schema schema;

    private final List<Opt> opts;
    private final List<Arg> args;
    private final List<Cmd> cmds;

    private Result result = null;

    private final Consumer<Result> enterShell;
    private final Consumer<Command> commandConsumer;

    private final InputStream inputStream;
    private final PrintStream outputStream;
    private final PrintStream errorStream;

    CLI(ArgsCliBuilder builder) {
        this.input = builder.getInput();
        this.schema = builder.getSchema();
        this.opts = builder.getOpts();
        this.args = builder.getArgs();
        this.cmds = builder.getCmds();
        this.inputStream = null;
        this.outputStream = null;
        this.errorStream = null;
        this.enterShell = null;
        this.commandConsumer = null;
    }

    CLI(ShellCliBuilder builder) {
        this.input = builder.getInput();
        this.schema = builder.getSchema();
        this.opts = builder.getOpts();
        this.args = builder.getArgs();
        this.cmds = builder.getCmds();
        this.inputStream = builder.getInputStream();
        this.outputStream = builder.getOutputStream();
        this.errorStream = builder.getErrorStream();
        this.enterShell = builder.getEnterShellConsumer();
        this.commandConsumer = builder.getDefaultCommandConsumer();
    }

    public static ArgsCliBuilder setArgs(String[] args) {
        return new ArgsCliBuilder(new Args(args), Schema.OPTIONS);
    }

    public static ArgsCliBuilder setArgs(String[] args, Schema schema) {
        return new ArgsCliBuilder(new Args(args), schema);
    }

    public static ShellCliBuilder useShell() {
        return new ShellCliBuilder();
    }

    public static ShellCliBuilder useShell(InputStream inputStream, PrintStream outputStream) {
        return new ShellCliBuilder(inputStream, outputStream);
    }

    public static ShellCliBuilder useShell(
            InputStream inputStream,
            PrintStream outputStream,
            PrintStream errorStream) {
        return new ShellCliBuilder(inputStream, outputStream, errorStream);
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

            ArgsParser parser = new ArgsParser(this.input, this.schema, this.opts, this.args, this.cmds);
            result = parser.parse();
        }
    }

    private void runShell() {
        ArgsParser parser = new ArgsParser(
                this.input,
                Schema.OPTIONS_ARGUMENTS,
                this.opts,
                this.args,
                null);
        result = parser.parse();

        // enter the shell

        if (enterShell != null) {
            enterShell.accept(result);
        }

        Scanner scanner = new Scanner(this.inputStream == null ? System.in : this.inputStream);

        while (true) {
            // print prompt string
            this.outputStream.print("> ");
            String line = scanner.nextLine();

            ArgsParser cmdParser = new ArgsParser(
                    Args.fromString(line),
                    Schema.COMMANDS,
                    null,
                    null,
                    this.cmds);

            try {
                result = cmdParser.parse();
                Command command = result.getCommand();
                if (command.getBlueprint().getName().equals("exit")) {
                    break;
                } else {
                    if (commandConsumer != null) {
                        commandConsumer.accept(command);
                    }
                    if (command.getBlueprint().getConsumer() != null) {
                        command.getBlueprint().getConsumer().accept(command);
                    }
                }
            } catch (Exception ex) {
                this.errorStream.println(ex.toString());
                this.errorStream.println();
            }
        }
    }
}
