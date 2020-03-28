package de.curbanov.clifw.command;

import org.junit.Test;

import static org.junit.Assert.*;

public class CmdTest {

    @Test
    public void simpleCmd() {
        Cmd cmd = Cmd.useName("git").build();
        assertFalse(cmd.hasOpts());
        assertFalse(cmd.hasArgs());
        assertEquals("git", cmd.getName());
        assertEquals("", cmd.getDescription());
    }

    @Test(expected = Exception.class)
    public void illegalCmd() {
        Cmd.useName("cmd")
                .description("description")
                .description("description")
                .build();
    }
}