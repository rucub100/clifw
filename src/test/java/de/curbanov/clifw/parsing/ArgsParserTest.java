package de.curbanov.clifw.parsing;

import de.curbanov.clifw.Args;
import de.curbanov.clifw.Schema;
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
        Collection<Option> options = List.of(Option.useChar('a').build());
        ArgsParser parser = new ArgsParser();

        Result result = parser.parse(args, schema, options);

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

    @Test(expected = Exception.class)
    public void unexpectedOption() {
        Args args = new Args(new String[] { "-x" });
        Schema schema = Schema.OPTIONS;
        Collection<Option> options = List.of(Option.useChar('a').build());
        ArgsParser parser = new ArgsParser();

        parser.parse(args, schema, options);
    }

    @Test(expected = Exception.class)
    public void missingRequiredOption() {
        Args args = new Args(new String[] { "-a" });
        Schema schema = Schema.OPTIONS;
        Collection<Option> options = List.of(
                Option.useChar('a').build(),
                Option.useChar('b').required().addArgument(int.class).build());
        ArgsParser parser = new ArgsParser();

        parser.parse(args, schema, options);
    }
}