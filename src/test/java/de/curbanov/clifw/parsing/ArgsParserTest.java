package de.curbanov.clifw.parsing;

import de.curbanov.clifw.Args;
import de.curbanov.clifw.Schema;
import de.curbanov.clifw.option.Option;

import de.curbanov.clifw.option.UserOption;
import org.junit.Test;

import java.util.Collection;
import java.util.List;

import static org.junit.Assert.*;

public class ArgsParserTest {

    @Test
    public void parseSingleOption() {
        Args args = new Args(new String[] { "-a" });
        Schema schema = Schema.OPTIONS;
        Collection<Option> options = List.of(Option.useChar('a').build());

        ArgsParser parser = new ArgsParser(args, schema, options);
        Result result = parser.parse();

        assertTrue(
                "result must contain user options",
                result.hasUserOptions());
        assertTrue(
                "result must contain exactly one user option",
                result.getUserOptions().size() == 1);
        assertFalse(
                "user option must not have args",
                result.getUserOptions().get(0).hasArgs());
        assertTrue(
                "user option is short",
                result.getUserOptions().get(0).isShort());
        assertEquals("a", result.getUserOptions().get(0).getId());
    }

    @Test(expected = ParsingException.class)
    public void unexpectedOption() {
        Args args = new Args(new String[] { "-x" });
        Schema schema = Schema.OPTIONS;
        Collection<Option> options = List.of(Option.useChar('a').build());

        ArgsParser parser = new ArgsParser(args, schema, options);
        parser.parse();
    }

    @Test(expected = ParsingException.class)
    public void missingRequiredOption() {
        Args args = new Args(new String[] { "-a" });
        Schema schema = Schema.OPTIONS;
        Collection<Option> options = List.of(
                Option.useChar('a').build(),
                Option.useChar('b').required().addArgument(int.class).build());

        ArgsParser parser = new ArgsParser(args, schema, options);
        parser.parse();
    }

    @Test
    public void parsingOptionWithArg() {
        Args args = new Args(new String[] { "-a", "arg" });
        Schema schema = Schema.OPTIONS;
        Collection<Option> options = List.of(
                Option.useChar('a')
                        .required()
                        .addArgument(String.class)
                        .build());
        ArgsParser parser = new ArgsParser(args, schema, options);
        Result result = parser.parse();

        assertTrue(result.hasUserOptions());
        assertEquals(1, result.getUserOptions().size());

        UserOption uOpt = result.getUserOptions().get(0);
        assertTrue(uOpt.isShort());
        assertEquals("a", uOpt.getId());
        assertTrue(uOpt.hasArgs());
        assertEquals(1, uOpt.getArgs().size());
        assertEquals("arg", uOpt.getArgs().get(0));
    }

    @Test
    public void parsingOptionWithArgViaAssignmentOperator() {
        Args args = new Args(new String[] { "-a=arg" });
        Schema schema = Schema.OPTIONS;
        Collection<Option> options = List.of(
                Option.useChar('a')
                        .required()
                        .addArgument(String.class)
                        .build());

        ArgsParser parser = new ArgsParser(args, schema, options);
        Result result = parser.parse();

        assertTrue(result.hasUserOptions());
        assertEquals(1, result.getUserOptions().size());

        UserOption uOpt = result.getUserOptions().get(0);
        assertTrue(uOpt.isShort());
        assertEquals("a", uOpt.getId());
        assertTrue(uOpt.hasArgs());
        assertEquals(1, uOpt.getArgs().size());
        assertEquals("arg", uOpt.getArgs().get(0));
    }

    @Test
    public void parsingOptionWithArgs() {
        Args args = new Args(new String[] { "-a", "arg1", "arg2", "arg3" });
        Schema schema = Schema.OPTIONS;
        Collection<Option> options = List.of(
                Option.useChar('a')
                        .required()
                        .addArgument(String.class)
                        .addArgument(String.class)
                        .addArgument(String.class)
                        .build());

        ArgsParser parser = new ArgsParser(args, schema, options);
        Result result = parser.parse();

        assertTrue(result.hasUserOptions());
        assertEquals(1, result.getUserOptions().size());

        UserOption uOpt = result.getUserOptions().get(0);
        assertTrue(uOpt.isShort());
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
        Collection<Option> options = List.of(
                Option.useChar('a')
                        .required()
                        .addArgument(String.class)
                        .build());

        ArgsParser parser = new ArgsParser(args, schema, options);
        parser.parse();
    }

    @Test(expected = ParsingException.class)
    public void illegalArg() {
        Args args = new Args(new String[] { "-a", "abc" });
        Schema schema = Schema.OPTIONS;
        Collection<Option> options = List.of(
                Option.useChar('a')
                        .required()
                        .addArgument(int.class)
                        .build());

        ArgsParser parser = new ArgsParser(args, schema, options);
        parser.parse();
    }
}