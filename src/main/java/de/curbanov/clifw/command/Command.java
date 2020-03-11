package de.curbanov.clifw.command;

public class Command {

    private final Cmd blueprint;

    public Command(Cmd cmd) {
        this.blueprint = cmd;
    }

    public Cmd getBlueprint() {
        return this.blueprint;
    }
}
