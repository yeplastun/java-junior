package com.acme.edu.command;

import java.util.Arrays;

public class IntArrayLoggerStrategy extends LoggerStrategy {
    @Override
    public String getPrefix() {
        return "primitives array";
    }

    @Override
    public String format(Object o) {
        return String.format("%s: %s", getPrefix(), Arrays.toString((int[]) o).replace('[', '{').replace(']', '}'));
    }
}
