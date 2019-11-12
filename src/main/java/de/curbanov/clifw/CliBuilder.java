package de.curbanov.clifw;

import de.curbanov.clifw.command.Command;
import de.curbanov.clifw.option.Option;

public interface CliBuilder<T extends CliBuilder<? extends T>> {

    T addOption(Option option);
    T addOptions(Option... options);
    T addCommand(Command command);
    T addCommands(Command... commands);
    CLI build();
}
