package com.acme.edu.command;

public class ObjectLoggerCommand extends LoggerCommand {
    @Override
    public String getPrefix() {
        return "reference";
    }
}
