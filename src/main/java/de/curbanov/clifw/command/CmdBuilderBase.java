package de.curbanov.clifw.command;

import de.curbanov.clifw.argument.Arg;
import de.curbanov.clifw.option.Opt;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

abstract class CmdBuilderBase<T extends CmdBuilderBase<T>> implements CmdBuilder<T> {

    private String name = "";
    private String description = "";
    private List<Opt> opts = new ArrayList<>();
    private List<Arg> args = new ArrayList<>();
    private Consumer<Command> consumer = null;

    String getName() {
        return name;
    }

    String getDescription() {
        return this.description;
    }

    List<Opt> getOpts() {
        return this.opts;
    }

    List<Arg> getArgs() {
        return this.args;
    }

    Consumer<Command> getConsumer() {
        return this.consumer;
    }

    T name(String name) {
        if (this.name.isEmpty()) {
            this.name = name;
        } else {
            throw new IllegalStateException();
        }

        return (T) this;
    }

    @Override
    public T description(String description) {
        if (this.description.isEmpty()) {
            this.description = description;
        } else {
            throw new IllegalStateException();
        }

        return (T) this;
    }

    @Override
    public T addOpt(Opt opt) {
        this.opts.add(opt);
        return (T) this;
    }

    @Override
    public T addOpts(Opt... opts) {
        for (Opt opt : opts) {
            this.opts.add(opt);
        }

        return (T) this;
    }

    @Override
    public T addArg(Arg arg) {
        this.args.add(arg);
        return (T) this;
    }

    @Override
    public T addArgs(Arg... args) {
        for (Arg arg : args) {
            this.args.add(arg);
        }

        return (T) this;
    }

    @Override
    public T consumer(Consumer<Command> consumer) {
        if (this.consumer == null) {
            this.consumer = consumer;
        } else {
            throw new IllegalStateException();
        }
        return (T) this;
    }

    @Override
    public Cmd build() {
        return new Cmd(this);
    }
}
