package com.acme.edu;

public class TrivialLogFormatter implements LogFormatter {
    @Override
    public String format(String message) {
        return message;
    }
}
