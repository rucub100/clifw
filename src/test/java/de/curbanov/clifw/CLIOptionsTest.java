package de.curbanov.clifw;

import de.curbanov.clifw.argument.Arg;
import de.curbanov.clifw.option.Opt;

import de.curbanov.clifw.option.Option;
import de.curbanov.clifw.parsing.ParsingException;
import de.curbanov.clifw.parsing.Result;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class CLIOptionsTest {

    @Test
    public void singleOption() {
        String[] args = new String[] { "-a" };
        CLI cli = CLI.setArgs(args)
                .addOpt(Opt
                        .useChar('a')
                        .description("this is a short test option")
                        .build())
                .build();

        assertFalse(cli.hasResult());
        assertNull(cli.getResult());

        cli.run();

        Result res = cli.getResult();
        assertTrue(cli.hasResult());
        assertNotNull(res);
        assertTrue(res.hasOptions());
        assertEquals(1, res.getOptions().size());

        Option opt = res.getOptions().get(0);
        assertFalse(opt.hasArgs());
        assertEquals("a", opt.getId());
    }

    @Test
    public void singleOptionNotRequiredAndAbsent() {
        String[] args = new String[0];
        CLI cli = CLI.setArgs(args)
                .addOpt(Opt
                        .useChar('a')
                        .description("this is a short test option")
                        .build())
                .build();

        assertFalse(cli.hasResult());
        assertNull(cli.getResult());

        cli.run();

        Result res = cli.getResult();
        assertTrue(cli.hasResult());
        assertNotNull(res);
        assertFalse(res.hasOptions());
        assertEquals(0, res.getOptions().size());
    }

    @Test
    public void singleLongOption() {
        String[] args = new String[] { "--name" };
        CLI cli = CLI.setArgs(args)
                .addOpt(Opt
                        .useName("name")
                        .description("this is a long test option")
                        .build())
                .build();

        assertFalse(cli.hasResult());
        assertNull(cli.getResult());

        cli.run();

        Result res = cli.getResult();
        assertTrue(cli.hasResult());
        assertNotNull(res);
        assertTrue(res.hasOptions());
        assertEquals(1, res.getOptions().size());

        Option opt = res.getOptions().get(0);
        assertFalse(opt.hasArgs());
        assertEquals("name", opt.getId());
    }

    @Test
    public void singleOptionWithStringArg() {
        String[] args = new String[] { "-a", "test" };
        CLI cli = CLI.setArgs(args)
                .addOpt(Opt
                        .useChar('a')
                        .addArg(Arg.of(String.class).build())
                        .description("this is a short test option with an arg")
                        .build())
                .build();

        cli.run();

        Result res = cli.getResult();
        assertEquals(1, res.getOptions().size());
        Option opt = res.getOptions().get(0);
        assertTrue(opt.hasArgs());
        List<String> optArgs = opt.getArgs();
        assertEquals(1, optArgs.size());
        assertEquals("test", optArgs.get(0));
    }

    @Test
    public void singleLongShortOptionWithArgs() {
        String[] args = new String[] { "--name", "123456789", "test", "b" };
        CLI cli = CLI.setArgs(args)
                .addOpt(Opt
                        .useChar('a')
                        .addArg(Arg.of(long.class).build())
                        .addArg(Arg.of(String.class).build())
                        .addArg(Arg.of(char.class).build())
                        .description("this is a short test option with an arg")
                        .longId("name")
                        .build())
                .build();

        cli.run();

        Result res = cli.getResult();
        assertEquals(1, res.getOptions().size());
        Option opt = res.getOptions().get(0);
        assertTrue(opt.hasArgs());
        List<String> optArgs = opt.getArgs();
        assertEquals(3, optArgs.size());
        assertEquals("123456789", optArgs.get(0));
        assertEquals("test", optArgs.get(1));
        assertEquals("b", optArgs.get(2));
    }

    @Test
    public void multipleOptions() {
        String[] args = new String[] { "--name" };
        CLI cli = CLI.setArgs(args)
                .addOpts(
                        Opt.useChar('a').build(),
                        Opt.useName("name").build())
                .build();

        cli.run();

        Result res = cli.getResult();
        assertEquals(1, res.getOptions().size());
        Option opt = res.getOptions().get(0);
        assertFalse(opt.hasArgs());
        assertEquals("name", opt.getId());
    }

    @Test(expected = ParsingException.class)
    public void missingRequiredOption() {
        String[] args = new String[] { "-a" };
        CLI cli = CLI.setArgs(args)
                .addOpt(Opt
                        .useChar('a')
                        .description("this is a short test option")
                        .build())
                .addOpt(Opt
                        .useChar('b')
                        .addArg(Arg.of(int.class).build())
                        .required()
                        .build())
                .build();

        cli.run();
    }
}
