package com.acme.edu.rx.response;

import com.acme.edu.rx.exception.LoggingException;

public class LogResponse {
    private final String logMessage;
    private final LoggingException exception;

    public LogResponse(Throwable exception) {
        LoggingException castedException = (LoggingException) exception;
        this.logMessage = (castedException).getLogMessage();
        this.exception = castedException;
    }

    public LogResponse(String logMessage) {
        this.logMessage = logMessage;
        this.exception = null;
    }

    public boolean isSuccessful() {
        return exception == null;
    }

    public String getLogMessage() {
        return logMessage;
    }

    public LoggingException getException() {
        return exception;
    }
}
