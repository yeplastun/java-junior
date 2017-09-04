package com.acme.edu.rx.saver;

import com.acme.edu.rx.exception.IOLogMessageException;

public class ConsoleLogSaver implements LogSaver {
    @Override
    public void save(String message) throws IOLogMessageException {
        System.out.println(message);
    }
}
