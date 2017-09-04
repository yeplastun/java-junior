package com.acme.edu.rx.formatter;

public interface LogFormatter {
    String format(Object message);

    String format(Boolean message);

    String format(Byte message);

    String format(Integer message);

    String format(Character message);

    String format(String message);

    String format(int[] message);
}
