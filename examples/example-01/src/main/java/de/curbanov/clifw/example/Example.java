package de.curbanov.clifw.example;

import de.curbanov.clifw.CLI;
import de.curbanov.clifw.command.Cmd;
import de.curbanov.clifw.command.Command;
import de.curbanov.clifw.parsing.Result;

public class Example {

    public static void main(String[] args) {
        CLI.useShell()
                .whenEntering(Example::enterShell)
                .globalLevelCommandConsumer(Example::globalCommandConsumer)
                .addCmd(Cmd.useName("test").consumer(Example::testConsumer).build())
                .addCmd(Cmd.useName("command2").build())
                .addCmd(Cmd.useName("command3").build())
                .build()
                .run();
    }

    private static void enterShell(Result result) {
        System.out.println("Welcome to your interactive shell!");
        System.out.println("==================================");
        System.out.println();
    }

    private static void globalCommandConsumer(Command command) {
        System.out.println("The global level command consumer can handle all commands");
        System.out.println("Your command: " + command.getBlueprint().getName());
        System.out.println();
    }

    private static void testConsumer(Command command) {
        System.out.println("The local level command consumer can handle a specific command");
        System.out.println();
    }
}
