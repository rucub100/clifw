package de.curbanov.clifw.option;

import org.junit.Test;

import static org.junit.Assert.*;

public class OptionTest {

    @Test
    public void simpleOption() {
        Option opt = Option
                .useChar('a')
                .build();

        assertNotNull(opt);
        assertTrue(opt.hasShortName());
        assertFalse(opt.hasLongName());
        assertFalse(opt.hasArgs());
        assertFalse(opt.isRequired());

        assertEquals(0, opt.getArgsCount());
        assertNotNull(opt.getLongName());
        assertTrue(opt.getLongName().isEmpty());
        assertEquals("a", opt.getShortName());
    }

    @Test
    public void simpleLongOption() {
        Option opt = Option
                .useName("name")
                .build();

        assertNotNull(opt);
        assertFalse(opt.hasShortName());
        assertTrue(opt.hasLongName());
        assertFalse(opt.hasArgs());
        assertFalse(opt.isRequired());

        assertEquals(0, opt.getArgsCount());
        assertNotNull(opt.getShortName());
        assertTrue(opt.getShortName().isEmpty());
        assertEquals("name", opt.getLongName());
    }

    @Test
    public void simpleOptionWithShortAndLongName() {
        Option opt = Option
                .useChar('a')
                .longId("name")
                .build();

        assertTrue(opt.hasShortName());
        assertTrue(opt.hasLongName());
    }

    @Test
    public void simpleOptionWithShortAndLongName2() {
        Option opt = Option
                .useName("name")
                .shortId('a')
                .build();

        assertTrue(opt.hasShortName());
        assertTrue(opt.hasLongName());
    }

    @Test
    public void simpleOptionWithArgs() {
        Option opt = Option
                .useChar('a')
                .addArgument(int.class)
                .addArgument(String.class)
                .build();

        assertTrue(opt.hasArgs());
        assertEquals(2, opt.getArgsCount());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void unexpectedGetArg() {
        Option.useChar('a').build().getArgType(0);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void unexpectedGetArgIndex() {
        Option.useChar('a')
                .addArgument(int.class)
                .build().getArgType(1);
    }

    @Test
    public void requiredOption() {
        Option opt = Option
                .useChar('a')
                .required()
                .addArgument(int.class)
                .build();

        assertTrue(opt.isRequired());
    }

    @Test(expected = IllegalStateException.class)
    public void requiredOptionWithoutArgs() {
        Option.useChar('a')
                .required()
                .build();
    }

    @Test(expected = IllegalStateException.class)
    public void unexpectedMultipleCall() {
        Option.useChar('a')
                .shortId('b')
                .build();
    }

    @Test(expected = IllegalStateException.class)
    public void unexpectedMultipleCall2() {
        Option.useName("name")
                .longId("another")
                .build();
    }

    @Test(expected = IllegalStateException.class)
    public void unexpectedMultipleCall3() {
        Option.useChar('a')
                .addArgument(int.class)
                .required()
                .required()
                .build();
    }

    @Test(expected = IllegalStateException.class)
    public void unexpectedMultipleCall4() {
        Option.useChar('a')
                .description("description1")
                .description("description2")
                .build();
    }

    @Test
    public void simpleOptionWithAllArgs() {
        Option opt = Option
                .useChar('a')
                .addArgument(boolean.class)
                .addArgument(byte.class)
                .addArgument(char.class)
                .addArgument(double.class)
                .addArgument(float.class)
                .addArgument(int.class)
                .addArgument(long.class)
                .addArgument(short.class)
                .addArgument(String.class)
                .build();

        assertTrue(opt.hasArgs());
        assertEquals(9, opt.getArgsCount());
    }

    @Test
    public void simpleOptionWithAllArgs2() {
        Option opt = Option
                .useChar('a')
                .addArgument(Boolean.class)
                .addArgument(Byte.class)
                .addArgument(Character.class)
                .addArgument(Double.class)
                .addArgument(Float.class)
                .addArgument(Integer.class)
                .addArgument(Long.class)
                .addArgument(Short.class)
                .build();

        assertTrue(opt.hasArgs());
        assertEquals(8, opt.getArgsCount());
    }

    @Test(expected = IllegalArgumentException.class)
    public void uexpectedVoidArg() {
        Option.useChar('a')
                .addArgument(void.class)
                .build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void unexpectedArgType() {
        Option.useChar('a')
                .addArgument(Object.class)
                .build();
    }
}