package com.acme.edu.rx.exception;

public class IOLogMessageException extends LogMessageException {
    public IOLogMessageException(Object logMessage) {
        super(logMessage);
    }

    public IOLogMessageException(String message, Object logMessage) {
        super(message, logMessage);
    }

    public IOLogMessageException(String message, Throwable cause, Object logMessage) {
        super(message, cause, logMessage);
    }
}
