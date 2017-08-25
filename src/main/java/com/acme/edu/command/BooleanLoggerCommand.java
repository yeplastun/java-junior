package com.acme.edu.command;

import com.acme.edu.State;

public class BooleanLoggerCommand extends LoggerCommand {
    private State state;

    public BooleanLoggerCommand(State state) {
        this.state = state;
    }

    @Override
    public String getPrefix() {
        return "primitive";
    }
}
