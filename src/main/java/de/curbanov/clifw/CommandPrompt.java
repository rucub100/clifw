package de.curbanov.clifw;

import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import java.io.*;
import java.util.Scanner;
import java.util.function.Function;

class CommandPrompt {

    private final String promptString = "> ";
    private final Function<String, String> prompt;
    private final Scanner scanner;

    private final InputStream inputStream;
    private final PrintStream outputStream;
    private final PrintStream errorStream;

    private CommandPrompt(
            Function<String, String> prompt,
            InputStream inputStream,
            PrintStream outputStream,
            PrintStream errorStream) {
        this.prompt = prompt;
        this.scanner = null;
        this.inputStream = inputStream;
        this.outputStream = outputStream;
        this.errorStream = errorStream;
    }

    private CommandPrompt(InputStream inputStream, PrintStream outputStream, PrintStream errorStream) {
        this.prompt = this::simpleNextLine;
        this.scanner = new Scanner(inputStream);
        this.inputStream = inputStream;
        this.outputStream = outputStream;
        this.errorStream = errorStream;
    }

    private String simpleNextLine(String prompt) {
        this.outputStream.println(prompt);
        return this.scanner.nextLine();
    }

    String nextLine() {
        return prompt.apply(promptString);
    }

    InputStream getInputStream() {
        return inputStream;
    }

    PrintStream getOutputStream() {
        return outputStream;
    }

    PrintStream getErrorStream() {
        return errorStream;
    }

    public static CommandPromptBuilder builder(
            InputStream inputStream,
            PrintStream outputStream,
            PrintStream errorStream) {
        return new CommandPromptBuilder(inputStream, outputStream, errorStream);
    }

    public static class CommandPromptBuilder {

        private final InputStream inputStream;
        private final PrintStream outputStream;
        private final PrintStream errorStream;

        private CommandPromptBuilder(InputStream inputStream, PrintStream outputStream, PrintStream errorStream) {
            this.inputStream = inputStream;
            this.outputStream = outputStream;
            this.errorStream = errorStream;
        }

        public CommandPrompt build() {
            Terminal terminal;

            if (System.console() != null) {
                try {
                    terminal = TerminalBuilder.builder()
                            .system(true)
                            .streams(this.inputStream, this.outputStream)
                            .build();
                } catch (IOException e) {
                    return new CommandPrompt(this.inputStream, this.outputStream, this.errorStream);
                }
            } else {
                return new CommandPrompt(this.inputStream, this.outputStream, this.errorStream);
            }

            LineReader lineReader = LineReaderBuilder.builder().terminal(terminal).build();
            return new CommandPrompt(
                    lineReader::readLine,
                    this.inputStream,
                    this.outputStream,
                    this.errorStream);
        }
    }
}
