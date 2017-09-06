package com.acme.edu.rx.formatter;

import java.util.Arrays;

public class TestLogFormatter implements LogFormatter {
    @Override
    public String format(Object message) {
        return message.toString();
    }

    @Override
    public String format(Boolean message) {
        return message.toString();

    }

    @Override
    public String format(Byte message) {
        return message.toString();
    }

    @Override
    public String format(Integer message) {
        return message.toString();
    }

    @Override
    public String format(Character message) {
        return message.toString();
    }

    @Override
    public String format(String message) {
        return message;
    }

    @Override
    public String format(int[] message) {
        return Arrays.toString(message);
    }
}
