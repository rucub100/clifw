package de.curbanov.clifw;

import de.curbanov.clifw.argument.Arg;
import de.curbanov.clifw.argument.Argument;
import de.curbanov.clifw.command.Cmd;
import de.curbanov.clifw.command.Command;
import de.curbanov.clifw.option.Opt;
import de.curbanov.clifw.option.Option;
import de.curbanov.clifw.parsing.Result;
import org.junit.Test;

import static org.junit.Assert.*;

public class CLICommandsTest {

    @Test
    public void simpleCommand() {
        String[] args = new String[] { "ls", "-l" };
        Opt opt = Opt.useChar('l').build();
        Cmd cmd = Cmd.useName("ls").addOpt(opt).build();
        CLI cli = CLI.setArgs(args, Schema.COMMANDS)
                .addCommand(cmd)
                .build();

        cli.run();

        assertTrue(cli.hasResult());

        Result result = cli.getResult();

        assertTrue(result.isCommand());
        assertFalse(result.hasOptions());
        assertFalse(result.hasArguments());

        Command command = result.getCommand();
        assertNotNull(command);
        assertTrue(command.hasOptions());
        assertFalse(command.hasArguments());
        assertEquals(cmd, command.getBlueprint());

        assertEquals(1, command.getOptions().size());
        Option option = command.getOptions().get(0);
        assertNotNull(option);
        assertEquals("l", option.getId());
        assertFalse(option.hasArgs());
        assertEquals(opt, option.getBlueprint());
    }

    @Test
    public void commandWithArgs() {
        String[] args = new String[] { "ls", "123", "arg2" };
        Arg arg1 = Arg.of(int.class).build();
        Arg arg2 = Arg.of(String.class).build();
        Cmd cmd = Cmd.useName("ls").addArgs(arg1, arg2).build();
        CLI cli = CLI.setArgs(args, Schema.COMMANDS)
                .addCommand(cmd)
                .build();

        cli.run();

        assertTrue(cli.hasResult());

        Command command = cli.getResult().getCommand();
        assertTrue(command.hasArguments());
        assertFalse(command.hasOptions());
        assertEquals(2, command.getArguments().size());

        Argument<Integer> argument1 = (Argument<Integer>) command.getArguments().get(0);
        Argument<String> argument2 = (Argument<String>) command.getArguments().get(1);
        assertEquals(arg1, argument1.getBlueprint());
        assertEquals(arg2, argument2.getBlueprint());
        assertEquals(123, (int) argument1.getValue());
        assertEquals("arg2", argument2.getValue());
    }

    @Test
    public void commandWithOptsAndArgs() {
        String[] args = new String[] { "cmd", "-a=3", "arg1", "arg2" };
        Opt opt = Opt.useChar('a').addArg(Arg.of(int.class).build()).build();
        Arg arg1 = Arg.of(String.class).build();
        Arg arg2 = Arg.of(String.class).build();
        Cmd cmd = Cmd.useName("cmd").addArgs(arg1, arg2).addOpt(opt).build();
        CLI cli = CLI.setArgs(args, Schema.COMMANDS)
                .addCommand(cmd)
                .build();

        cli.run();

        Command command = cli.getResult().getCommand();
        assertTrue(command.hasOptions());
        assertTrue(command.hasArguments());
        assertEquals(2, command.getArguments().size());
        assertEquals(1, command.getOptions().size());
        assertTrue(command.getOptions().get(0).hasArgs());
        assertEquals(1, command.getOptions().get(0).getArgs().size());
    }
}
