package de.curbanov.clifw;

public enum Schema {

    OPTIONS(true, false, false),
    ARGUMENTS(false, false, false),
    OPTIONS_ARGUMENTS(true, false, false),
    COMMANDS(false, true, false),
    SHELL(true, true, true);

    private final boolean allowsOptions;
    private final boolean allowsCommands;
    private final boolean isShell;

    Schema(final boolean allowsOptions, final boolean allowsCommands, final boolean isShell) {
        this.allowsOptions = allowsOptions;
        this.allowsCommands = allowsCommands;
        this.isShell = isShell;
    }

    public final boolean allowsOptions() {
        return this.allowsOptions;
    }

    public final boolean allowsCommands() {
        return this.allowsCommands;
    }

    public final boolean isShell() { return this.isShell; }
}
