package de.curbanov.clifw;

import de.curbanov.clifw.argument.Arg;
import de.curbanov.clifw.command.Cmd;
import de.curbanov.clifw.option.Opt;

import java.util.ArrayList;
import java.util.List;

public abstract class CliBuilderBase<T extends CliBuilderBase<T>> implements CliBuilder<T> {

    private final Args input;
    private final Schema schema;

    private final List<Arg> args;
    private final List<Opt> opts;
    private final List<Cmd> cmds;

    CliBuilderBase(Args input, Schema schema) {
        this.input = input;
        this.schema = schema;
        this.args = schema.allowsArgutments() ? new ArrayList<>() : null;
        this.opts = schema.allowsOptions() ? new ArrayList<>() : null;
        this.cmds = schema.allowsCommands() ? new ArrayList<>() : null;
    }

    Args getInput() {
        return this.input;
    }

    Schema getSchema() {
        return this.schema;
    }

    List<Arg> getArgs() {
        return this.args;
    }

    List<Opt> getOpts() {
        return this.opts;
    }

    List<Cmd> getCmds() {
        return this.cmds;
    }

    @Override
    public T addArg(Arg arg) {
        if (schema.allowsArgutments()) {
            this.args.add(arg);
        } else {
            throw new UnsupportedOperationException();
        }

        return (T) this;
    }

    @Override
    public T addArgs(Arg... args) {
        if (schema.allowsArgutments()) {
            for (Arg arg : args) {
                this.args.add(arg);
            }
        } else {
            throw new UnsupportedOperationException();
        }

        return (T) this;
    }

    @Override
    public T addOpt(Opt opt) {
        if (schema.allowsOptions()) {
            this.opts.add(opt);
        } else {
            throw new UnsupportedOperationException();
        }

        return (T) this;
    }

    @Override
    public T addOpts(Opt... opts) {
        if (schema.allowsOptions()) {
            for (Opt opt : opts) {
                this.opts.add(opt);
            }
        } else {
            throw new UnsupportedOperationException();
        }

        return (T) this;
    }

    @Override
    public T addCmd(Cmd cmd) {
        if (schema.allowsCommands()) {
            this.cmds.add(cmd);
        } else {
            throw new UnsupportedOperationException();
        }

        return (T) this;
    }

    @Override
    public T addCmds(Cmd... cmds) {
        if (schema.allowsCommands()) {
            for (Cmd cmd : cmds) {
                this.cmds.add(cmd);
            }
        } else {
            throw new UnsupportedOperationException();
        }

        return (T) this;
    }
}
