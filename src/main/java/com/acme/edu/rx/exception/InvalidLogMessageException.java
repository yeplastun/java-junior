package com.acme.edu.rx.exception;

public class InvalidLogMessageException extends LogMessageException {
    public InvalidLogMessageException(Object logMessage) {
        super(logMessage);
    }

    public InvalidLogMessageException(String message, Object logMessage) {
        super(message);
        this.logMessage = logMessage;
    }

    public InvalidLogMessageException(String message, Throwable cause, Object logMessage) {
        super(message, cause);
        this.logMessage = logMessage;
    }
}
