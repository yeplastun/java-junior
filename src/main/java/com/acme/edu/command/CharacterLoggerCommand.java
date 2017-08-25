package com.acme.edu.command;

import com.acme.edu.State;

public class CharacterLoggerCommand extends LoggerCommand {
    private State state;

    public CharacterLoggerCommand(State state) {
        this.state = state;
    }

    @Override
    public String getPrefix() {
        return "char";
    }
}
