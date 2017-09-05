package com.acme.edu.rx.response;

import com.acme.edu.rx.exception.LoggingException;

/**
 * Class for getting result from async log operations in case of success or fail.
 */
public class LogResponse {
    private final String logMessage;
    private final LoggingException exception;

    /**
     * Constructor for creating filled with {@link com.acme.edu.rx.exception.LoggingException} response.
     * {@link #isSuccessful()}will return {@code false}.
     *
     * @param exception - Thrown exception in log message processing.
     */
    public LogResponse(Throwable exception) {
        LoggingException castedException = (LoggingException) exception;
        this.logMessage = (castedException).getLogMessage();
        this.exception = castedException;
    }

    /**
     * Constructor for creating filled with {@link String} message which was successfully logged.
     * {@link #isSuccessful()}will return {@code true}.
     *
     * @param logMessage - Message which was successfully logged.
     */
    public LogResponse(String logMessage) {
        this.logMessage = logMessage;
        this.exception = null;
    }

    /**
     * @return {@link Boolean} - Indicates result of log processing.
     */
    public boolean isSuccessful() {
        return exception == null;
    }

    /**
     * @return {@link String} - Message which was successfully logged.
     */
    public String getLogMessage() {
        return logMessage;
    }

    /**
     * @return {@link LoggingException} - Thrown exception in log message processing.
     */
    public LoggingException getException() {
        return exception;
    }
}
