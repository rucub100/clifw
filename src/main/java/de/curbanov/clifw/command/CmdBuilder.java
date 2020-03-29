package de.curbanov.clifw.command;

import de.curbanov.clifw.argument.Arg;
import de.curbanov.clifw.option.Opt;

import java.util.function.Consumer;

public interface CmdBuilder<T extends CmdBuilder<? extends T>> {

    T description(String description);
    T addOpt(Opt opt);
    T addOpts(Opt... opts);
    T addArg(Arg arg);
    T addArgs(Arg... args);
    T consumer(Consumer<Command> consumer);
    Cmd build();
}
