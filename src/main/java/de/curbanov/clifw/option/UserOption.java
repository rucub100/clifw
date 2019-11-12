package de.curbanov.clifw.option;

import java.util.List;

public class UserOption {

    private final String id;
    private final boolean isShort;
    private final List<String> args;

    public UserOption(String id, boolean isShort, List<String> args) {
        this.id = id;
        this.isShort = isShort;
        this.args = args;
    }

    public UserOption(String id, boolean isShort) {
        this(id, isShort, null);
    }

    public String getId() {
        return id;
    }

    public boolean isShort() {
        return isShort;
    }

    public boolean hasArgs() {
        return !(args == null || args.isEmpty());
    }

    public List<String> getArgs() {
        return args;
    }
}
