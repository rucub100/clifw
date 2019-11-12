package de.curbanov.clifw.parsing;

import de.curbanov.clifw.option.UserOption;

import java.util.Collections;
import java.util.List;

public class Result {

    private final List<UserOption> userOptions;

    Result(List<UserOption> userOptions) {
        this.userOptions = userOptions;
    }

    public boolean hasUserOptions() {
        return this.userOptions != null && this.userOptions.size() > 0;
    }

    public List<UserOption> getUserOptions() {
        return Collections.unmodifiableList(this.userOptions);
    }
}
