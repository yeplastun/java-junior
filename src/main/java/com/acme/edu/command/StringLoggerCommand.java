package com.acme.edu.command;

import com.acme.edu.State;

public class StringLoggerCommand extends LoggerCommand {
    private State state;

    public StringLoggerCommand(State state) {
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
        if (o.equals(state.previousInstance)) {
            ++state.stringCounter;
            return;
        }

        print();
        state.previousInstance = o;
        state.stringCounter = 1;
    }

    @Override
    public void initialize(Object o) {
        state.stringCounter = 1;
    }

    @Override
    public String format(Object o) {
        if (state.stringCounter == 1) {
            return String.format("%s: %s", getPrefix(), o);
        }
        return String.format("%s: %s (x%d)", getPrefix(), o, state.stringCounter);
    }

    private void print() {

    }


}
