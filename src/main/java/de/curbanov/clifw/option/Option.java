package de.curbanov.clifw.option;

import java.util.List;

public class Option {

    private final Opt blueprint;
    private final List<String> args;

    public Option(Opt opt, List<String> args) {
        this.blueprint = opt;
        this.args = args;
    }

    public Option(Opt opt) {
        this(opt, null);
    }

    public Opt getBlueprint()
    {
        return this.blueprint;
    }

    public String getId() {
        return this.blueprint.hasShortName() ?
                this.blueprint.getShortName() :
                this.blueprint.getLongName();
    }

    public boolean hasArgs() {
        return !(args == null || args.isEmpty());
    }

    public List<String> getArgs() {
        return args;
    }
}
