package com.acme.edu.command;

import com.acme.edu.State;

public abstract class NumberLoggerCommand<N extends Number> extends LoggerCommand {
    protected final State state;

    public NumberLoggerCommand(State state) {
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
    public void initialize(Object o) {
        state.sum = (int) o;
    }

    @Override
    public String format(Object o) {
        return String.format("%s: %s", getPrefix(), state.sum);
    }

    public void print() {

    }
}
