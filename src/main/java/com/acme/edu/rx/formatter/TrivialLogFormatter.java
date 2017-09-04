package com.acme.edu.rx.formatter;

import java.util.Arrays;

public class TrivialLogFormatter implements LogFormatter {

    @Override
    public String format(Object message) {
        return "reference: " + message.toString();
    }

    @Override
    public String format(Boolean message) {
        return "primitive: " + message.toString();
    }

    @Override
    public String format(Byte message) {
        return "primitive: " + message.toString();
    }

    @Override
    public String format(Integer message) {
        return "primitive: " + message.toString();
    }

    @Override
    public String format(Character message) {
        return "char: " + message.toString();
    }

    @Override
    public String format(String message) {
        return "string: " + message;
    }

    @Override
    public String format(int[] message) {
        return "primitives array: " + Arrays.toString(message);
    }
}
