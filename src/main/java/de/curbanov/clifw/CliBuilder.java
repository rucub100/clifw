package de.curbanov.clifw;

import de.curbanov.clifw.argument.Arg;
import de.curbanov.clifw.command.Cmd;
import de.curbanov.clifw.option.Opt;

public interface CliBuilder<T extends CliBuilder<? extends T>> {

    T addOpt(Opt opt);
    T addOpts(Opt... opts);
    T addArg(Arg arg);
    T addArgs(Arg... args);
    T addCmd(Cmd cmd);
    T addCmds(Cmd... cmds);
    CLI build();
}
