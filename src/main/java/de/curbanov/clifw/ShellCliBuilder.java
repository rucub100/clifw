package de.curbanov.clifw;

public class ShellCliBuilder extends CliBuilderBase<ShellCliBuilder> {

    ShellCliBuilder(Args args) {
        super(args, Schema.SHELL);
    }

    public CLI build() {
        return super.build();
    }
}
