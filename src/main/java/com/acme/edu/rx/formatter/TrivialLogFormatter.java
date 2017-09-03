package com.acme.edu.rx.formatter;

public class TrivialLogFormatter implements LogFormatter {

    @Override
    public String format(Object message) {
        return "reference: " + message.toString();
    }

    @Override
    public String format(Integer message) {
        return "primitive: " + message.toString();
    }

    @Override
    public String format(String message) {
        return "string: " + message;
    }
}
