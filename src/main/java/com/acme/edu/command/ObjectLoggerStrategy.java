package com.acme.edu.command;

public class ObjectLoggerStrategy extends LoggerStrategy {
    @Override
    public String getPrefix() {
        return "reference";
    }
}
