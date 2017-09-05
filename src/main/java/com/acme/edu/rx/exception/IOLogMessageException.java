package com.acme.edu.rx.exception;

public class IOLogMessageException extends LogMessageException {
    public IOLogMessageException(String logMessage) {
        super(logMessage);
    }

    public IOLogMessageException(String message, String logMessage) {
        super(message, logMessage);
    }

    public IOLogMessageException(String message, Throwable cause, String logMessage) {
        super(message, cause, logMessage);
    }
}
