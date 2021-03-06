package de.curbanov.clifw;

import de.curbanov.clifw.command.Cmd;
import de.curbanov.clifw.command.Command;
import de.curbanov.clifw.parsing.Result;

import java.io.InputStream;
import java.io.PrintStream;
import java.util.function.Consumer;

public class ShellCliBuilder extends CliBuilderBase<ShellCliBuilder> {

    private final CommandPrompt commandPrompt;

    private final InputStream inputStream;
    private final PrintStream outputStream;
    private final PrintStream errorStream;

    private Consumer<Result> enterShellConsumer = null;
    private Consumer<Command> defaultCommandConsumer = null;

    ShellCliBuilder() {
        super(Args.EMPTY, Schema.SHELL);
        this.inputStream = System.in;
        this.outputStream = System.out;
        this.errorStream = System.err;
        commandPrompt = CommandPrompt.builder(this.inputStream, this.outputStream, this.errorStream).build();
    }

    ShellCliBuilder(InputStream inputStream, PrintStream outputStream, PrintStream errorStream) {
        super(Args.EMPTY, Schema.SHELL);
        this.inputStream = inputStream;
        this.outputStream = outputStream;
        this.errorStream = errorStream;
        commandPrompt = CommandPrompt.builder(this.inputStream, this.outputStream, this.errorStream).build();
    }

    CommandPrompt getCommandPrompt() {
        return this.commandPrompt;
    }

    Consumer<Result> getEnterShellConsumer() {
        return enterShellConsumer;
    }

    Consumer<Command> getDefaultCommandConsumer() {
        return defaultCommandConsumer;
    }

    public ShellCliBuilder whenEntering(Consumer<Result> consumer) {
        this.enterShellConsumer = consumer;
        return this;
    }

    public ShellCliBuilder globalLevelCommandConsumer(Consumer<Command> consumer) {
        this.defaultCommandConsumer = consumer;
        return this;
    }

    @Override
    public CLI build() {
        if (this.getCmds().stream().noneMatch(x -> x.getName().equals("exit"))) {
            this.addCmd(Cmd.useName("exit").build());
        }

        return new CLI(this);
    }
}
