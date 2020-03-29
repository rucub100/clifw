package de.curbanov.clifw;

import java.util.Iterator;

public class Args implements Iterable<String> {

    public static final Args EMPTY = new Args(new String[] {});

    private final String[] args;

    public Args(String[] args) {
        this.args = args;
    }

    public static Args fromString(String strArgs) {
        String[] tmp = strArgs.split("\\s");
        return new Args(tmp);
    }

    public int length() {
        return this.args.length;
    }

    public Iterator<String> iterator() {
        return new Iterator<>() {

            private int pos = 0;

            @Override
            public boolean hasNext() {
                return pos < args.length;
            }

            @Override
            public String next() {
                return args[pos++];
            }
        };
    }
}
