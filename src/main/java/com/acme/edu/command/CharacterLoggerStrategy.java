package com.acme.edu.command;

public class CharacterLoggerStrategy extends LoggerStrategy {
    @Override
    public String getPrefix() {
        return "char";
    }
}
