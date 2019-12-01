package de.curbanov.clifw.parsing;

public class ParsingException extends RuntimeException {

    private final Phase phase;

    public ParsingException(Phase phase) {
        this.phase = phase;
    }

    public ParsingException(Phase phase, String message) {
        super(message);
        this.phase = phase;
    }

    public ParsingException(Phase phase, String message, Throwable cause) {
        super(message, cause);
        this.phase = phase;
    }

    public ParsingException(Phase phase, Throwable cause) {
        super(cause);
        this.phase = phase;
    }

    public ParsingException(Phase phase, String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
        this.phase = phase;
    }

    public Phase getPhase() {
        return phase;
    }
}
