package com.acme.edu.rx.exception;

/**
 * Class of exceptions used for errors during business logic processing
 */
public class InvalidLogMessageException extends LoggingException {
    /**
     * Creates new {@link LoggingException} with message <b>message</b> and logMessage <b>logMessage</b>.
     * @param message Message for exception.
     * @param logMessage Message which logger failed to log.
     */
    public InvalidLogMessageException(String message, String logMessage) {
        super(message, logMessage);
    }
}
