package com.acme.edu.command;

import com.acme.edu.State;

public class StringLoggerStrategy extends LoggerStrategy {
    private State state;

    public StringLoggerStrategy(State state) {
        this.state = state;
    }

    @Override
    public String getPrefix() {
        return "string";
    }

    @Override
    public boolean requiresCollection() {
        return true;
    }

    @Override
    public void collect(Object o) {
        if (o.equals(state.getPreviousInstance())) {
            state.setStringCounter(state.getStringCounter() + 1);
            return;
        }

        state.setObjectToPrint(state.getPreviousInstance());
        state.setPreviousInstance(o);
        state.setStringCounter(1);
    }

    @Override
    public void initialize(Object o) {
        state.setStringCounter(1);
    }

    @Override
    public String format(Object o) {
        if (state.getStringCounter() == 1) {
            return String.format("%s: %s", getPrefix(), o);
        }
        return String.format("%s: %s (x%d)", getPrefix(), o, state.getStringCounter());
    }
}
