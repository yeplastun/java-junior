package com.acme.edu.rx.exception;

public class InvalidLogMessageException extends LogMessageException {
    public InvalidLogMessageException(String message, String logMessage) {
        super(message, logMessage);
    }
}
