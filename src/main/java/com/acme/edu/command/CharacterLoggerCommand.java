package com.acme.edu.command;

public class CharacterLoggerCommand extends LoggerCommand {
    @Override
    public String getPrefix() {
        return "char";
    }
}
