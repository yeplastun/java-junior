package com.acme.edu.rx.formatter;

import java.util.Arrays;

/**
 * Provides interface for access to formatting logic component.
 */
public class TrivialLogFormatter implements LogFormatter {
    private static final String FORMAT = "%s: %s";

    private static final String REFERENCE_PREFIX = "reference";
    private static final String PRIMITIVE_PREFIX = "primitive";
    private static final String CHAR_PREFIX = "char";
    private static final String STRING_PREFIX = "string";
    private static final String PRIMITIVES_ARRAY_PREFIX = "primitives array";

    /**
     * Encapsulates formatting for instances of {@link Object} and other classes which don't have specific formatting.
     *
     * @param message Object which user wants to format.
     * @return String which contains the result of formatting.
     */
    @Override
    public String format(Object message) {
        return String.format(FORMAT, REFERENCE_PREFIX, message.toString());
    }

    /**
     * Encapsulates formatting for instances of {@link Boolean}.
     *
     * @param message Boolean which user wants to format.
     * @return String which contains the result of formatting.
     */
    @Override
    public String format(Boolean message) {
        return String.format(FORMAT, PRIMITIVE_PREFIX, message.toString());
    }

    /**
     * Encapsulates formatting for instances of {@link Byte}.
     *
     * @param message Byte which user wants to format.
     * @return String which contains the result of formatting.
     */
    @Override
    public String format(Byte message) {
        return String.format(FORMAT, PRIMITIVE_PREFIX, message.toString());
    }

    /**
     * Encapsulates formatting for instances of {@link Integer}.
     *
     * @param message Integer which user wants to format.
     * @return String which contains the result of formatting.
     */
    @Override
    public String format(Integer message) {
        return String.format(FORMAT, PRIMITIVE_PREFIX, message.toString());
    }

    /**
     * Encapsulates formatting for instances of {@link Character}.
     *
     * @param message Character which user wants to format.
     * @return String which contains the result of formatting.
     */
    @Override
    public String format(Character message) {
        return String.format(FORMAT, CHAR_PREFIX, message.toString());
    }

    /**
     * Encapsulates formatting for instances of {@link String}.
     *
     * @param message String which user wants to format.
     * @return String which contains the result of formatting.
     */
    @Override
    public String format(String message) {
        return String.format(FORMAT, STRING_PREFIX, message);
    }

    /**
     * Encapsulates formatting for arrays of integer numbers.
     *
     * @param message Array which user wants to format.
     * @return String which contains the result of formatting.
     */
    @Override
    public String format(int[] message) {
        return String.format(FORMAT, PRIMITIVES_ARRAY_PREFIX, Arrays.toString(message));
    }
}
