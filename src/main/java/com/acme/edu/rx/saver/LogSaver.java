package com.acme.edu.rx.saver;

import com.acme.edu.rx.exception.IOLogMessageException;

/**
 * Abstraction for saving logging messages to specified destination resource
 */
@FunctionalInterface
public interface LogSaver {
    /**
     * Saves message to specified resource.
     * @param message Message to be saved.
     * @throws IOLogMessageException If destination resource is closed.
     */
    void save(String message) throws IOLogMessageException;
}
