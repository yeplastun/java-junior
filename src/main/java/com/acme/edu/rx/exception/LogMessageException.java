package com.acme.edu.rx.exception;

public class LogMessageException extends Exception {
    Object logMessage;

    LogMessageException(Object logMessage) {
        this.logMessage = logMessage;
    }

    LogMessageException(String message, Object logMessage) {
        super(message);
        this.logMessage = logMessage;
    }

    LogMessageException(String message, Throwable cause, Object logMessage) {
        super(message, cause);
        this.logMessage = logMessage;
    }

    public Object getLogMessage() {
        return logMessage;
    }

    public void setLogMessage(Object logMessage) {
        this.logMessage = logMessage;
    }

}
