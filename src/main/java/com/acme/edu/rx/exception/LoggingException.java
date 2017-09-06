package com.acme.edu.rx.exception;

/**
 * Base exception class for logger.
 */
public class LoggingException extends Exception {
    private final String logMessage;

    /**
     * Creates general exception of logging operation.
     * @param message Exception details.
     * @param logMessage Message user tried to log.
     */
    LoggingException(String message, String logMessage) {
        super(message);
        this.logMessage = logMessage;
    }

    /**
     * Creates general exception of logging operation.
     * @param message Exception details.
     * @param cause Unhandle exception which caused creation of logging exception.
     * @param logMessage Message user tried to log.
     */
    LoggingException(String message, Throwable cause, String logMessage) {
        super(message, cause);
        this.logMessage = logMessage;
    }

    /**
     * Getter for logMessage field.
     * @return Value of logMessage field.
     */
    public String getLogMessage() {
        return logMessage;
    }
}
