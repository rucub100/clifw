package de.curbanov.clifw;

import de.curbanov.clifw.command.Cmd;
import org.apache.commons.io.output.TeeOutputStream;
import org.junit.Test;

import java.io.*;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.Assert.*;

public class CLIShellTest {

    @Test
    public void simpleEmptyShell() throws IOException {
        PipedOutputStream src = new PipedOutputStream();
        PrintStream printer = new PrintStream(src);
        InputStream inputStream = new PipedInputStream(src);
        PrintStream outputStream = System.out;
        PipedOutputStream pipe = new PipedOutputStream();
        PipedInputStream testError = new PipedInputStream();
        pipe.connect(testError);
        TeeOutputStream errorStream = new TeeOutputStream(pipe, System.err);

        printer.println("exit");

        CLI.useShell(inputStream, outputStream, new PrintStream(errorStream)).build().run();
        assertEquals(0, testError.available());
    }

    @Test
    public void simpleShell() throws IOException {
        PipedOutputStream src = new PipedOutputStream();
        PrintStream printer = new PrintStream(src);
        InputStream inputStream = new PipedInputStream(src);
        PrintStream printStream = System.out;
        PipedOutputStream pipe = new PipedOutputStream();
        PipedInputStream testError = new PipedInputStream();
        pipe.connect(testError);
        TeeOutputStream errorStream = new TeeOutputStream(pipe, System.err);

        printer.println("hi");
        printer.println("exit");

        CLI cli = CLI.useShell(inputStream, printStream, new PrintStream(errorStream))
                .addCmd(Cmd.useName("hi").build())
                .build();

        cli.run();
        assertEquals(0, testError.available());
    }

    @Test
    public void commandNotFound() throws IOException {
        PipedOutputStream src = new PipedOutputStream();
        PrintStream printer = new PrintStream(src);
        InputStream inputStream = new PipedInputStream(src);
        PrintStream printStream = System.out;
        PipedOutputStream pipe = new PipedOutputStream();
        PipedInputStream testError = new PipedInputStream();
        pipe.connect(testError);
        TeeOutputStream errorStream = new TeeOutputStream(pipe, System.err);

        printer.println("hi");
        printer.println("exit");

        CLI cli = CLI.useShell(inputStream, printStream, new PrintStream(errorStream))
                .addCmd(Cmd.useName("hello").build())
                .build();

        cli.run();
        assertTrue(testError.available() > 0);
        assertEquals(
                "de.curbanov.clifw.parsing.ParsingException: Command 'hi' not found!",
                new String(testError.readNBytes(testError.available())).replaceAll("[\\n\\r]", ""));
    }

    @Test
    public void enterShellConsumer() throws IOException {
        PipedOutputStream src = new PipedOutputStream();
        PrintStream printer = new PrintStream(src);
        InputStream inputStream = new PipedInputStream(src);
        PrintStream printStream = System.out;
        PipedOutputStream pipe = new PipedOutputStream();
        PipedInputStream testError = new PipedInputStream();
        pipe.connect(testError);
        TeeOutputStream errorStream = new TeeOutputStream(pipe, System.err);

        printer.println("hi");
        printer.println("exit");
        AtomicBoolean consumed = new AtomicBoolean(false);

        CLI.useShell(inputStream, printStream, new PrintStream(errorStream))
                .addCmd(Cmd.useName("hi").build())
                .whenEntering(x -> consumed.set(true))
                .build()
                .run();

        assertTrue(consumed.get());
    }

    @Test
    public void globalLevelConsumer() throws IOException {
        PipedOutputStream src = new PipedOutputStream();
        PrintStream printer = new PrintStream(src);
        InputStream inputStream = new PipedInputStream(src);
        PrintStream printStream = System.out;
        PipedOutputStream pipe = new PipedOutputStream();
        PipedInputStream testError = new PipedInputStream();
        pipe.connect(testError);
        TeeOutputStream errorStream = new TeeOutputStream(pipe, System.err);

        printer.println("hi");
        printer.println("exit");
        AtomicBoolean consumed = new AtomicBoolean(false);

        CLI.useShell(inputStream, printStream, new PrintStream(errorStream))
                .addCmd(Cmd.useName("hi").build())
                .globalLevelCommandConsumer(x -> consumed.set(x.getBlueprint().getName().equals("hi")))
                .build()
                .run();

        assertTrue(consumed.get());
    }

    @Test
    public void localLevelConsumer() throws IOException {
        PipedOutputStream src = new PipedOutputStream();
        PrintStream printer = new PrintStream(src);
        InputStream inputStream = new PipedInputStream(src);
        PrintStream printStream = System.out;
        PipedOutputStream pipe = new PipedOutputStream();
        PipedInputStream testError = new PipedInputStream();
        pipe.connect(testError);
        TeeOutputStream errorStream = new TeeOutputStream(pipe, System.err);

        printer.println("hi");
        printer.println("exit");
        AtomicBoolean consumed = new AtomicBoolean(false);

        CLI.useShell(inputStream, printStream, new PrintStream(errorStream))
                .addCmd(Cmd.useName("hi")
                        .consumer(x -> consumed.set(x.getBlueprint().getName().equals("hi")))
                        .build())
                .build()
                .run();

        assertTrue(consumed.get());
    }

    @Test
    public void globalAndLocalLevelConsumers() throws IOException {
        PipedOutputStream src = new PipedOutputStream();
        PrintStream printer = new PrintStream(src);
        InputStream inputStream = new PipedInputStream(src);
        PrintStream printStream = System.out;
        PipedOutputStream pipe = new PipedOutputStream();
        PipedInputStream testError = new PipedInputStream();
        pipe.connect(testError);
        TeeOutputStream errorStream = new TeeOutputStream(pipe, System.err);

        printer.println("hi");
        printer.println("exit");
        AtomicBoolean consumedGlobally = new AtomicBoolean(false);
        AtomicBoolean consumedLocally = new AtomicBoolean(false);

        CLI.useShell(inputStream, printStream, new PrintStream(errorStream))
                .addCmd(Cmd.useName("hi")
                        .consumer(x -> consumedLocally.set(x.getBlueprint().getName().equals("hi")))
                        .build())
                .globalLevelCommandConsumer(x -> consumedGlobally.set(
                        x.getBlueprint().getName().equals("hi") && !consumedLocally.get()))
                .build()
                .run();

        assertTrue(consumedGlobally.get());
        assertTrue(consumedLocally.get());
    }
}
