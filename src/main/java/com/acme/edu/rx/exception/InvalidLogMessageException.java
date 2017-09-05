package com.acme.edu.rx.exception;

public class InvalidLogMessageException extends LogMessageException {
    public InvalidLogMessageException(String logMessage) {
        super(logMessage);
    }

    public InvalidLogMessageException(String message, String logMessage) {
        super(message, logMessage);
    }

    public InvalidLogMessageException(String message, Throwable cause, String logMessage) {
        super(message, cause, logMessage);
    }
}
