package de.curbanov.clifw.argument;

import org.junit.Test;

import java.lang.reflect.Array;

import static org.junit.Assert.*;

public class ArgTest {

    @Test
    public void simpleArg() {
        Arg arg = Arg.of(String.class).build();

        assertNotNull(arg);
        assertEquals(String.class, arg.getClazz());
        assertEquals("", arg.getDescription());
        assertEquals("", arg.getPlaceholder());
    }

    @Test
    public void booleanArg() {
        Arg arg = Arg.of(boolean.class).build();
        Arg arg2 = Arg.of(Boolean.class).build();

        assertEquals(boolean.class, arg.getClazz());
        assertEquals(Boolean.class, arg2.getClazz());
    }

    @Test
    public void byteArg() {
        Arg arg = Arg.of(byte.class).build();
        Arg arg2 = Arg.of(Byte.class).build();

        assertEquals(byte.class, arg.getClazz());
        assertEquals(Byte.class, arg2.getClazz());
    }

    @Test
    public void charArg() {
        Arg arg = Arg.of(char.class).build();
        Arg arg2 = Arg.of(Character.class).build();

        assertEquals(char.class, arg.getClazz());
        assertEquals(Character.class, arg2.getClazz());
    }

    @Test
    public void doubleArg() {
        Arg arg = Arg.of(double.class).build();
        Arg arg2 = Arg.of(Double.class).build();

        assertEquals(double.class, arg.getClazz());
        assertEquals(Double.class, arg2.getClazz());
    }

    @Test
    public void floatArg() {
        Arg arg = Arg.of(float.class).build();
        Arg arg2 = Arg.of(Float.class).build();

        assertEquals(float.class, arg.getClazz());
        assertEquals(Float.class, arg2.getClazz());
    }

    @Test
    public void intArg() {
        Arg arg = Arg.of(int.class).build();
        Arg arg2 = Arg.of(Integer.class).build();

        assertEquals(int.class, arg.getClazz());
        assertEquals(Integer.class, arg2.getClazz());
    }

    @Test
    public void longArg() {
        Arg arg = Arg.of(long.class).build();
        Arg arg2 = Arg.of(Long.class).build();

        assertEquals(long.class, arg.getClazz());
        assertEquals(Long.class, arg2.getClazz());
    }

    @Test
    public void shortArg() {
        Arg arg = Arg.of(short.class).build();
        Arg arg2 = Arg.of(Short.class).build();

        assertEquals(short.class, arg.getClazz());
        assertEquals(Short.class, arg2.getClazz());
    }

    @Test
    public void argWithDescriptionAndPlaceholder() {
        Arg arg = Arg.of(String.class)
                .description("description")
                .placeholder("placeholder")
                .build();

        assertNotNull(arg);
        assertEquals(String.class, arg.getClazz());
        assertEquals("description", arg.getDescription());
        assertEquals("placeholder", arg.getPlaceholder());
    }

    @Test(expected = Exception.class)
    public void illegalMultiDescriptionArg() {
        Arg.of(String.class)
                .description("a")
                .description("b")
                .build();
    }

    @Test(expected = Exception.class)
    public void illegalMultiPlaceholderArg() {
        Arg.of(String.class)
                .placeholder("a")
                .placeholder("b")
                .build();
    }

    @Test(expected = Exception.class)
    public void illegalVoidTypeArg() {
        Arg.of(void.class).build();
    }

    @Test(expected = Exception.class)
    public void illegalObjectTypeArg() {
        Arg.of(Object.class).build();
    }

    @Test(expected = Exception.class)
    public void illegalArrayTypeArg() {
        Arg.of(Array.class).build();
    }
}
