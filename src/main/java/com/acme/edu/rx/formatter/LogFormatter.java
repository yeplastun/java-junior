package com.acme.edu.rx.formatter;

/**
 * Provides interface for access to formatting logic component.
 */
public interface LogFormatter {
    /**
     * Encapsulates formatting for instances of {@link Object} and other classes which don't have specific formatting.
     *
     * @param message Object which user wants to format.
     * @return String which contains the result of formatting.
     */
    String format(Object message);

    /**
     * Encapsulates formatting for instances of {@link Boolean}.
     *
     * @param message Boolean which user wants to format.
     * @return String which contains the result of formatting.
     */
    String format(Boolean message);

    /**
     * Encapsulates formatting for instances of {@link Byte}.
     *
     * @param message Byte which user wants to format.
     * @return String which contains the result of formatting.
     */
    String format(Byte message);

    /**
     * Encapsulates formatting for instances of {@link Integer}.
     *
     * @param message Integer which user wants to format.
     * @return String which contains the result of formatting.
     */
    String format(Integer message);

    /**
     * Encapsulates formatting for instances of {@link Character}.
     *
     * @param message Character which user wants to format.
     * @return String which contains the result of formatting.
     */
    String format(Character message);

    /**
     * Encapsulates formatting for instances of {@link String}.
     *
     * @param message String which user wants to format.
     * @return String which contains the result of formatting.
     */
    String format(String message);

    /**
     * Encapsulates formatting for arrays of integer numbers.
     *
     * @param message Array which user wants to format.
     * @return String which contains the result of formatting.
     */
    String format(int[] message);
}
