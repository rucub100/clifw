package de.curbanov.clifw.command;

import de.curbanov.clifw.argument.Arg;
import de.curbanov.clifw.option.Opt;

public interface CmdBuilder<T extends CmdBuilder<? extends T>> {

    T name(String name);
    T description(String description);
    T addOpt(Opt opt);
    T addOpts(Opt... opts);
    T addArg(Arg arg);
    T addArgs(Arg... args);
    Cmd build();
}
