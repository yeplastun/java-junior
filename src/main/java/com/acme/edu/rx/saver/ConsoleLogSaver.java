package com.acme.edu.rx.saver;

import com.acme.edu.rx.exception.IOLogMessageException;

/**
 * Class for saving logging messages to the console.
 */
public class ConsoleLogSaver implements LogSaver {
    /**
     * Saves message to the console.
     * @param message Message to be saved.
     */
    @Override
    public void save(String message) {
        System.out.println(message);
    }
}
