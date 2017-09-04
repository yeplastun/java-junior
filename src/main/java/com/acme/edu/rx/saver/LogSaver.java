package com.acme.edu.rx.saver;

import com.acme.edu.rx.exception.IOLogMessageException;

@FunctionalInterface
public interface LogSaver {
    void save(String message) throws IOLogMessageException;
}
