package de.curbanov.clifw;

import de.curbanov.clifw.argument.Arg;
import de.curbanov.clifw.argument.Argument;
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
}