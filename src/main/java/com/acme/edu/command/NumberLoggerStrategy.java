package com.acme.edu.command;

import com.acme.edu.State;

public abstract class NumberLoggerStrategy extends LoggerStrategy {
    protected final State state;

    public NumberLoggerStrategy(State state) {
        this.state = state;
    }

    @Override
    public String getPrefix() {
        return "primitive";
    }

    @Override
    public boolean requiresCollection() {
        return true;
    }

    @Override
    public String format(Object o) {
        return String.format("%s: %s", getPrefix(), o);
    }
}
