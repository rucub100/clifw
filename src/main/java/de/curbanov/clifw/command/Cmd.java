package de.curbanov.clifw.command;

import de.curbanov.clifw.argument.Arg;
import de.curbanov.clifw.option.Opt;

import java.util.Collections;
import java.util.List;

public class Cmd {

    private final String name;
    private final String description;
    private final List<Opt> opts;
    private final List<Arg> args;

    Cmd(CmdBuilderBase builder) {
        this.name = builder.getName();
        this.description = builder.getDescription();
        this.opts = builder.getOpts();
        this.args = builder.getArgs();
    }

    public static DefaultCmdBuilder useName(String name) {
        return new DefaultCmdBuilder().name(name);
    }

    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return this.description;
    }

    public List<Opt> getOpts() {
        return Collections.unmodifiableList(this.opts);
    }

    public List<Arg> getArgs() {
        return Collections.unmodifiableList(this.args);
    }
}