package de.curbanov.clifw;

import de.curbanov.clifw.option.Option;

import org.junit.Test;

import static org.junit.Assert.*;

public class CLITest {

    @Test
    public void singleOption() {
        String[] args = new String[] { "-a" };
        CLI cli = CLI.setArgs(args)
                .addOption(Option
                        .useChar('a')
                        .description("this is a short test option")
                        .build())
                .build();

        assertFalse(cli.hasResult());
        assertNull(cli.getResult());

        cli.run();

        assertTrue(cli.hasResult());
        assertNotNull(cli.getResult());
    }
}
