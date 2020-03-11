package de.curbanov.clifw.option;

import de.curbanov.clifw.argument.Arg;
import org.junit.Test;

import static org.junit.Assert.*;

public class OptionTest {

    @Test
    public void simpleOption() {
        Opt opt = Opt
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
        Opt opt = Opt
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
        Opt opt = Opt
                .useChar('a')
                .longId("name")
                .build();

        assertTrue(opt.hasShortName());
        assertTrue(opt.hasLongName());
    }

    @Test
    public void simpleOptionWithShortAndLongName2() {
        Opt opt = Opt
                .useName("name")
                .shortId('a')
                .build();

        assertTrue(opt.hasShortName());
        assertTrue(opt.hasLongName());
    }

    @Test
    public void simpleOptionWithArgs() {
        Opt opt = Opt
                .useChar('a')
                .addArg(Arg.of(int.class).build())
                .addArg(Arg.of(String.class).build())
                .build();

        assertTrue(opt.hasArgs());
        assertEquals(2, opt.getArgsCount());
    }

    @Test(expected = UnsupportedOperationException.class)
    public void unexpectedGetArg() {
        Opt.useChar('a').build().getArgType(0);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void unexpectedGetArgIndex() {
        Opt.useChar('a')
                .addArg(Arg.of(int.class).build())
                .build().getArgType(1);
    }

    @Test
    public void requiredOption() {
        Opt opt = Opt
                .useChar('a')
                .required()
                .addArg(Arg.of(int.class).build())
                .build();

        assertTrue(opt.isRequired());
    }

    @Test(expected = IllegalStateException.class)
    public void requiredOptionWithoutArgs() {
        Opt.useChar('a')
                .required()
                .build();
    }

    @Test(expected = IllegalStateException.class)
    public void unexpectedMultipleCall() {
        Opt.useChar('a')
                .shortId('b')
                .build();
    }

    @Test(expected = IllegalStateException.class)
    public void unexpectedMultipleCall2() {
        Opt.useName("name")
                .longId("another")
                .build();
    }

    @Test(expected = IllegalStateException.class)
    public void unexpectedMultipleCall3() {
        Opt.useChar('a')
                .addArg(Arg.of(int.class).build())
                .required()
                .required()
                .build();
    }

    @Test(expected = IllegalStateException.class)
    public void unexpectedMultipleCall4() {
        Opt.useChar('a')
                .description("description1")
                .description("description2")
                .build();
    }

    @Test
    public void simpleOptionWithAllArgs() {
        Opt opt = Opt
                .useChar('a')
                .addArg(Arg.of(boolean.class).build())
                .addArg(Arg.of(byte.class).build())
                .addArg(Arg.of(char.class).build())
                .addArg(Arg.of(double.class).build())
                .addArg(Arg.of(float.class).build())
                .addArg(Arg.of(int.class).build())
                .addArg(Arg.of(long.class).build())
                .addArg(Arg.of(short.class).build())
                .addArg(Arg.of(String.class).build())
                .build();

        assertTrue(opt.hasArgs());
        assertEquals(9, opt.getArgsCount());
    }

    @Test
    public void simpleOptionWithAllArgs2() {
        Opt opt = Opt
                .useChar('a')
                .addArg(Arg.of(Boolean.class).build())
                .addArg(Arg.of(Byte.class).build())
                .addArg(Arg.of(Character.class).build())
                .addArg(Arg.of(Double.class).build())
                .addArg(Arg.of(Float.class).build())
                .addArg(Arg.of(Integer.class).build())
                .addArg(Arg.of(Long.class).build())
                .addArg(Arg.of(Short.class).build())
                .build();

        assertTrue(opt.hasArgs());
        assertEquals(8, opt.getArgsCount());
    }

    @Test(expected = IllegalArgumentException.class)
    public void unexpectedVoidArg() {
        Opt.useChar('a')
                .addArg(Arg.of(void.class).build())
                .build();
    }

    @Test(expected = IllegalArgumentException.class)
    public void unexpectedArgType() {
        Opt.useChar('a')
                .addArg(Arg.of(Object.class).build())
                .build();
    }
}