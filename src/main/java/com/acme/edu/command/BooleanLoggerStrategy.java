package com.acme.edu.command;

public class BooleanLoggerStrategy extends LoggerStrategy {
    @Override
    public String getPrefix() {
        return "primitive";
    }
}
