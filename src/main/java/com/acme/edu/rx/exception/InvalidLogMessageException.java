package com.acme.edu.rx.exception;

public class InvalidLogMessageException extends LogMessageException {
    public InvalidLogMessageException(Object logMessage) {
        super(logMessage);
    }

    public InvalidLogMessageException(String message, Object logMessage) {
        super(message, logMessage);
    }

    public InvalidLogMessageException(String message, Throwable cause, Object logMessage) {
        super(message, cause, logMessage);
    }
}
