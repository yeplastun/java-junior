package com.acme.edu.command;

public abstract class LoggerStrategy {
    public abstract String getPrefix();

    public boolean requiresCollection() {
        return false;
    }

    public void collect(Object o) { }

    public void initialize(Object o) { }

    public String format(Object o) {
        return String.format("%s: %s", getPrefix(), o.toString());
    }
}
