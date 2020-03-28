package de.curbanov.clifw;

import de.curbanov.clifw.argument.Arg;
import de.curbanov.clifw.argument.Argument;
import de.curbanov.clifw.option.Opt;
import de.curbanov.clifw.option.Option;
import de.curbanov.clifw.parsing.Result;
import org.junit.Test;

import static org.junit.Assert.*;

public class CLIOptionsAndArgumentsTest {

    @Test
    public void simpleOptAndArg() {
        String[] args = new String[] { "-o", "arg1" };

        Opt opt = Opt.useChar('o').build();
        Arg arg = Arg.of(String.class).build();
        CLI cli = CLI.setArgs(args, Schema.OPTIONS_ARGUMENTS)
                .addOption(opt)
                .addArg(arg)
                .build();

        cli.run();

        assertTrue(cli.hasResult());

        Result result = cli.getResult();
        assertNotNull(result);
        assertTrue(result.hasOptions());
        assertTrue(result.hasArguments());
        assertFalse(result.isCommand());
        assertEquals(1, result.getOptions().size());
        assertEquals(1, result.getArguments().size());

        Option option = result.getOptions().get(0);
        Argument argument = result.getArguments().get(0);

        assertFalse(option.hasArgs());
        assertEquals("o", option.getId());
        assertSame(opt, option.getBlueprint());

        assertEquals("arg1", argument.getValue());
        assertSame(arg, argument.getBlueprint());
    }

    @Test
    public void simpleOptWithArgAndArg() {
        String[] args = new String[] { "-o=oarg", "arg1" };

        Arg oarg = Arg.of(String.class).build();
        Opt opt = Opt.useChar('o').addArg(oarg).build();
        Arg arg = Arg.of(String.class).build();
        CLI cli = CLI.setArgs(args, Schema.OPTIONS_ARGUMENTS)
                .addOption(opt)
                .addArg(arg)
                .build();

        cli.run();

        assertTrue(cli.hasResult());

        Result result = cli.getResult();
        assertNotNull(result);
        assertTrue(result.hasOptions());
        assertTrue(result.hasArguments());
        assertFalse(result.isCommand());
        assertEquals(1, result.getOptions().size());
        assertEquals(1, result.getArguments().size());

        Option option = result.getOptions().get(0);
        Argument argument = result.getArguments().get(0);

        assertTrue(option.hasArgs());
        assertEquals("o", option.getId());
        assertSame(opt, option.getBlueprint());
        assertEquals(1, option.getArgs().size());
        assertEquals("oarg", option.getArgs().get(0));

        assertEquals("arg1", argument.getValue());
        assertSame(arg, argument.getBlueprint());
    }

    @Test
    public void simpleOptWithArgAndArg2() {
        String[] args = new String[] { "-o", "oarg", "arg1" };

        Arg oarg = Arg.of(String.class).build();
        Opt opt = Opt.useChar('o').addArg(oarg).build();
        Arg arg = Arg.of(String.class).build();
        CLI cli = CLI.setArgs(args, Schema.OPTIONS_ARGUMENTS)
                .addOption(opt)
                .addArg(arg)
                .build();

        cli.run();

        assertTrue(cli.hasResult());

        Result result = cli.getResult();
        assertNotNull(result);
        assertTrue(result.hasOptions());
        assertTrue(result.hasArguments());
        assertFalse(result.isCommand());
        assertEquals(1, result.getOptions().size());
        assertEquals(1, result.getArguments().size());

        Option option = result.getOptions().get(0);
        Argument argument = result.getArguments().get(0);

        assertTrue(option.hasArgs());
        assertEquals("o", option.getId());
        assertSame(opt, option.getBlueprint());
        assertEquals(1, option.getArgs().size());
        assertEquals("oarg", option.getArgs().get(0));

        assertEquals("arg1", argument.getValue());
        assertSame(arg, argument.getBlueprint());
    }

    @Test
    public void simpleOptWithArgsAndArg() {
        String[] args = new String[] { "--option", "123", "oarg2", "arg1" };

        Arg oarg1 = Arg.of(byte.class).build();
        Arg oarg2 = Arg.of(String.class).build();
        Opt opt = Opt.useChar('o').longId("option").addArgs(oarg1, oarg2).build();
        Arg arg = Arg.of(String.class).build();
        CLI cli = CLI.setArgs(args, Schema.OPTIONS_ARGUMENTS)
                .addOption(opt)
                .addArg(arg)
                .build();

        cli.run();

        assertTrue(cli.hasResult());

        Result result = cli.getResult();
        assertNotNull(result);
        assertTrue(result.hasOptions());
        assertTrue(result.hasArguments());
        assertFalse(result.isCommand());
        assertEquals(1, result.getOptions().size());
        assertEquals(1, result.getArguments().size());

        Option option = result.getOptions().get(0);
        Argument argument = result.getArguments().get(0);

        assertTrue(option.hasArgs());
        assertEquals("o", option.getId());
        assertSame(opt, option.getBlueprint());
        assertEquals(2, option.getArgs().size());
        assertEquals("123", option.getArgs().get(0));
        assertEquals("oarg2", option.getArgs().get(1));

        assertEquals("arg1", argument.getValue());
        assertSame(arg, argument.getBlueprint());
    }
}