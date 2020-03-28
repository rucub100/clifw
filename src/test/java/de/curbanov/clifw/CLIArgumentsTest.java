package de.curbanov.clifw;

import de.curbanov.clifw.argument.Arg;
import de.curbanov.clifw.argument.Argument;
import de.curbanov.clifw.parsing.ParsingException;
import de.curbanov.clifw.parsing.Result;
import org.junit.Test;

import static org.junit.Assert.*;

public class CLIArgumentsTest {

    @Test
    public void singleArgument() {
        String[] args = new String[] { "arg1" };
        CLI cli = CLI.setArgs(args, Schema.ARGUMENTS)
                .addArg(Arg.of(String.class).build())
                .build();

        assertFalse(cli.hasResult());
        assertNull(cli.getResult());

        cli.run();

        assertTrue(cli.hasResult());
        assertNotNull(cli.getResult());

        Result res = cli.getResult();
        assertFalse(res.hasOptions());
        assertFalse(res.isCommand());
        assertTrue(res.hasArguments());
        assertEquals(1, res.getArguments().size());

        Argument<String> arg = (Argument<String>) res.getArguments().get(0);
        assertEquals("arg1", arg.getValue());
    }

    @Test
    public void multipleArgument() {
        String[] args = new String[] { "arg1", "123", "true" };
        CLI cli = CLI.setArgs(args, Schema.ARGUMENTS)
                .addArg(Arg.of(String.class).build())
                .addArg(Arg.of(int.class).build())
                .addArg(Arg.of(boolean.class).build())
                .build();

        cli.run();

        assertTrue(cli.hasResult());

        Result res = cli.getResult();
        assertFalse(res.hasOptions());
        assertFalse(res.isCommand());
        assertTrue(res.hasArguments());
        assertEquals(3, res.getArguments().size());

        assertEquals("arg1", res.getArguments().get(0).getValue());
        assertEquals(123, res.getArguments().get(1).getValue());
        assertEquals(true, res.getArguments().get(2).getValue());
    }

    @Test(expected = Exception.class)
    public void missingArgument() {
        String[] args = new String[] { "arg1" };
        CLI cli = CLI.setArgs(args, Schema.ARGUMENTS)
                .addArg(Arg.of(String.class).build())
                .addArg(Arg.of(String.class).build())
                .build();

        cli.run();
    }

    @Test(expected = ParsingException.class)
    public void tooManyArgument() {
        String[] args = new String[] { "arg1", "arg2", "arg3" };
        CLI cli = CLI.setArgs(args, Schema.ARGUMENTS)
                .addArg(Arg.of(String.class).build())
                .addArg(Arg.of(String.class).build())
                .build();

        cli.run();
    }

    @Test(expected = ParsingException.class)
    public void wrongArgumentType() {
        String[] args = new String[] { "arg1", "notAnInteger" };
        CLI cli = CLI.setArgs(args, Schema.ARGUMENTS)
                .addArg(Arg.of(String.class).build())
                .addArg(Arg.of(int.class).build())
                .build();

        cli.run();
    }
}