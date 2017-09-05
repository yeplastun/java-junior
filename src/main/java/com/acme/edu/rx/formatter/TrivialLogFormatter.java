package com.acme.edu.rx.formatter;

import java.util.Arrays;

/**
 * Provides interface for access to formatting logic component.
 */
public class TrivialLogFormatter implements LogFormatter {
    /**
     * Encapsulates formatting for instances of {@link Object} and other classes which don't have specific formatting.
     *
     * @param message Object which user wants to format.
     * @return String which contains the result of formatting.
     */
    @Override
    public String format(Object message) {
        return "reference: " + message.toString();
    }

    /**
     * Encapsulates formatting for instances of {@link Boolean}.
     *
     * @param message Boolean which user wants to format.
     * @return String which contains the result of formatting.
     */
    @Override
    public String format(Boolean message) {
        return formatPrimitive(message);
    }

    /**
     * Encapsulates formatting for instances of {@link Byte}.
     *
     * @param message Byte which user wants to format.
     * @return String which contains the result of formatting.
     */
    @Override
    public String format(Byte message) {
        return formatPrimitive(message);
    }

    /**
     * Encapsulates formatting for instances of {@link Integer}.
     *
     * @param message Integer which user wants to format.
     * @return String which contains the result of formatting.
     */
    @Override
    public String format(Integer message) {
        return formatPrimitive(message);
    }

    /**
     * Encapsulates formatting for instances of {@link Character}.
     *
     * @param message Character which user wants to format.
     * @return String which contains the result of formatting.
     */
    @Override
    public String format(Character message) {
        return "char: " + message.toString();
    }

    /**
     * Encapsulates formatting for instances of {@link String}.
     *
     * @param message String which user wants to format.
     * @return String which contains the result of formatting.
     */
    @Override
    public String format(String message) {
        return "string: " + message;
    }

    /**
     * Encapsulates formatting for arrays of integer numbers.
     *
     * @param message Array which user wants to format.
     * @return String which contains the result of formatting.
     */
    @Override
    public String format(int[] message) {
        return "primitives array: " + Arrays.toString(message);
    }

    private String formatPrimitive(Object message) {
        return "primitive: " + message.toString();
    }
}
