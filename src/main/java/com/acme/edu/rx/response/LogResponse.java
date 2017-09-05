package com.acme.edu.rx.response;

import com.acme.edu.rx.exception.LogMessageException;

public class LogResponse {
    private final String logMessage;
    private final LogMessageException exception;

    public LogResponse(Throwable exception) {
        LogMessageException castedException = (LogMessageException) exception;
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

    public LogMessageException getException() {
        return exception;
    }
}
