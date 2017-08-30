package com.acme.edu;

public class TrivialLogFormatter implements LogFormatter {
    @Override
    public String format(String message) {
        if (message == null) {
            throw new IllegalArgumentException("cannot format null object");
        }

        return message;
    }
}
