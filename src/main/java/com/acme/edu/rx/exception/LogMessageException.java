package com.acme.edu.rx.exception;

public class LogMessageException extends Exception {
    private final String logMessage;

    LogMessageException(String message, String logMessage) {
        super(message);
        this.logMessage = logMessage;
    }

    LogMessageException(String message, Throwable cause, String logMessage) {
        super(message, cause);
        this.logMessage = logMessage;
    }

    public Object getLogMessage() {
        return logMessage;
    }
}
