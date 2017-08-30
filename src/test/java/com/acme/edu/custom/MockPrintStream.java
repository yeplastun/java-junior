package com.acme.edu.custom;

import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

public class MockPrintStream extends PrintStream {
    private final List<String> strings = new ArrayList<>();

    public MockPrintStream() {
        super(System.out);
    }

    @Override
    public void println(String s) {
        strings.add(s);
    }

    public List<String> getStrings() {
        return strings;
    }
}
