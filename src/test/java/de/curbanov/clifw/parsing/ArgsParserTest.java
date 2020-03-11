package de.curbanov.clifw.parsing;

import de.curbanov.clifw.Args;
import de.curbanov.clifw.Schema;
import de.curbanov.clifw.argument.Arg;
import de.curbanov.clifw.option.Opt;

import de.curbanov.clifw.option.Option;
import org.junit.Test;

import java.util.Collection;
import java.util.List;

import static org.junit.Assert.*;

public class ArgsParserTest {

    @Test
    public void parseSingleOption() {
        Args args = new Args(new String[] { "-a" });
        Schema schema = Schema.OPTIONS;
        Collection<Opt> opts = List.of(Opt.useChar('a').build());

        ArgsParser parser = new ArgsParser(args, schema, opts, null);
        Result result = parser.parse();

        assertTrue(
                "result must contain user options",
                result.hasOptions());
        assertTrue(
                "result must contain exactly one user option",
                result.getOptions().size() == 1);
        assertFalse(
                "user option must not have args",
                result.getOptions().get(0).hasArgs());
        assertEquals("a", result.getOptions().get(0).getId());
    }

    @Test(expected = ParsingException.class)
    public void unexpectedOption() {
        Args args = new Args(new String[] { "-x" });
        Schema schema = Schema.OPTIONS;
        Collection<Opt> opts = List.of(Opt.useChar('a').build());

        ArgsParser parser = new ArgsParser(args, schema, opts, null);
        parser.parse();
    }

    @Test(expected = ParsingException.class)
    public void missingRequiredOption() {
        Args args = new Args(new String[] { "-a" });
        Schema schema = Schema.OPTIONS;
        Collection<Opt> opts = List.of(
                Opt.useChar('a').build(),
                Opt.useChar('b').required().addArg(Arg.of(int.class).build()).build());

        ArgsParser parser = new ArgsParser(args, schema, opts, null);
        parser.parse();
    }

    @Test
    public void parsingOptionWithArg() {
        Args args = new Args(new String[] { "-a", "arg" });
        Schema schema = Schema.OPTIONS;
        Collection<Opt> opts = List.of(
                Opt.useChar('a')
                        .required()
                        .addArg(Arg.of(String.class).build())
                        .build());
        ArgsParser parser = new ArgsParser(args, schema, opts, null);
        Result result = parser.parse();

        assertTrue(result.hasOptions());
        assertEquals(1, result.getOptions().size());

        Option uOpt = result.getOptions().get(0);
        assertEquals("a", uOpt.getId());
        assertTrue(uOpt.hasArgs());
        assertEquals(1, uOpt.getArgs().size());
        assertEquals("arg", uOpt.getArgs().get(0));
    }

    @Test
    public void parsingOptionWithArgViaAssignmentOperator() {
        Args args = new Args(new String[] { "-a=arg" });
        Schema schema = Schema.OPTIONS;
        Collection<Opt> opts = List.of(
                Opt.useChar('a')
                        .required()
                        .addArg(Arg.of(String.class).build())
                        .build());

        ArgsParser parser = new ArgsParser(args, schema, opts, null);
        Result result = parser.parse();

        assertTrue(result.hasOptions());
        assertEquals(1, result.getOptions().size());

        Option uOpt = result.getOptions().get(0);
        assertEquals("a", uOpt.getId());
        assertTrue(uOpt.hasArgs());
        assertEquals(1, uOpt.getArgs().size());
        assertEquals("arg", uOpt.getArgs().get(0));
    }

    @Test
    public void parsingOptionWithArgs() {
        Args args = new Args(new String[] { "-a", "arg1", "arg2", "arg3" });
        Schema schema = Schema.OPTIONS;
        Collection<Opt> opts = List.of(
                Opt.useChar('a')
                        .required()
                        .addArg(Arg.of(String.class).build())
                        .addArg(Arg.of(String.class).build())
                        .addArg(Arg.of(String.class).build())
                        .build());

        ArgsParser parser = new ArgsParser(args, schema, opts, null);
        Result result = parser.parse();

        assertTrue(result.hasOptions());
        assertEquals(1, result.getOptions().size());

        Option uOpt = result.getOptions().get(0);
        assertEquals("a", uOpt.getId());
        assertTrue(uOpt.hasArgs());
        assertEquals(3, uOpt.getArgs().size());
        assertEquals("arg1", uOpt.getArgs().get(0));
        assertEquals("arg2", uOpt.getArgs().get(1));
        assertEquals("arg3", uOpt.getArgs().get(2));
    }

    @Test(expected = ParsingException.class)
    public void missingArg() {
        Args args = new Args(new String[] { "-a" });
        Schema schema = Schema.OPTIONS;
        Collection<Opt> opts = List.of(
                Opt.useChar('a')
                        .required()
                        .addArg(Arg.of(String.class).build())
                        .build());

        ArgsParser parser = new ArgsParser(args, schema, opts, null);
        parser.parse();
    }

    @Test(expected = ParsingException.class)
    public void illegalArg() {
        Args args = new Args(new String[] { "-a", "abc" });
        Schema schema = Schema.OPTIONS;
        Collection<Opt> opts = List.of(
                Opt.useChar('a')
                        .required()
                        .addArg(Arg.of(int.class).build())
                        .build());

        ArgsParser parser = new ArgsParser(args, schema, opts, null);
        parser.parse();
    }

    @Test
    public void parseSingleLongOption() {
        Args args = new Args(new String[] { "--name" });
        Schema schema = Schema.OPTIONS;
        Collection<Opt> opts = List.of(Opt.useName("name").build());

        ArgsParser parser = new ArgsParser(args, schema, opts, null);
        Result result = parser.parse();

        assertTrue(
                "result must contain user options",
                result.hasOptions());
        assertTrue(
                "result must contain exactly one user option",
                result.getOptions().size() == 1);
        assertFalse(
                "user option must not have args",
                result.getOptions().get(0).hasArgs());
        assertEquals("name", result.getOptions().get(0).getId());
    }
}