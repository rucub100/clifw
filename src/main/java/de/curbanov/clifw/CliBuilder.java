package de.curbanov.clifw;

import de.curbanov.clifw.command.Cmd;
import de.curbanov.clifw.option.Opt;

public interface CliBuilder<T extends CliBuilder<? extends T>> {

    T addOption(Opt opt);
    T addOptions(Opt... opts);
    T addCommand(Cmd cmd);
    T addCommands(Cmd... cmds);
    CLI build();
}
