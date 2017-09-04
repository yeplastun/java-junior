package com.acme.edu.rx.saver;

import com.acme.edu.rx.exception.IOLogMessageException;

import java.io.IOException;

public class ExceptionTestSaver implements LogSaver {
    private static IOLogMessageException IOEXCEPTION =
            new IOLogMessageException("test exception", new IOException(), "test log message");

    public static IOLogMessageException getInstance() {
        return IOEXCEPTION;
    }

    @Override
    public void save(String message) throws IOLogMessageException {
        throw getInstance();
    }
}
