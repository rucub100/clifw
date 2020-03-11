package de.curbanov.clifw;

public enum Schema {

    OPTIONS(false, true, false, false),
    ARGUMENTS(true, false, false, false),
    OPTIONS_ARGUMENTS(true, true, false, false),
    COMMANDS(false, false, true, false),
    SHELL(true, true, true, true);

    private final boolean allowsArguments;
    private final boolean allowsOptions;
    private final boolean allowsCommands;
    private final boolean isShell;

    Schema(final boolean allowsArguments, final boolean allowsOptions, final boolean allowsCommands, final boolean isShell) {
        this.allowsArguments = allowsArguments;
        this.allowsOptions = allowsOptions;
        this.allowsCommands = allowsCommands;
        this.isShell = isShell;
    }

    public final boolean allowsArgutments() {
        return this.allowsArguments;
    }

    public final boolean allowsOptions() {
        return this.allowsOptions;
    }

    public final boolean allowsCommands() {
        return this.allowsCommands;
    }

    public final boolean isShell() { return this.isShell; }
}
