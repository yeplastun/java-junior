package com.acme.edu.rx.exception;

/**
 * Exception generated during logger output operation.
 */
public class IOLogMessageException extends LoggingException {
    /**
     * Creates new {@link IOLogMessageException} with message <b>message</b>, cause <b>cause</b> and logMessage <b>logMessage</b>.
     * @param message Message for exception.
     * @param cause Cause of exception.
     * @param logMessage Message which logger failed to log.
     */
    public IOLogMessageException(String message, Throwable cause, String logMessage) {
        super(message, cause, logMessage);
    }
}
