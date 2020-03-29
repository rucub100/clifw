package de.curbanov.clifw;

public class ArgsCliBuilder extends CliBuilderBase<ArgsCliBuilder> {

    ArgsCliBuilder(Args args, Schema schema) {
        super(args, schema);
    }

    @Override
    public CLI build() {
        return new CLI(this);
    }
}
