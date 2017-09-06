package com.acme.edu.rx.formatter;

import org.junit.Test;

import static org.fest.assertions.Assertions.assertThat;

public class TrivialLogFormatterTest {
    private LogFormatter formatter = new TrivialLogFormatter();

    @Test
    public void shouldFormatObject() {
        // Given, when
        Object message = new Object();

        // Then
        assertThat(formatter.format(message)).isEqualTo("reference: " + message.toString());
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    public void shouldFormatBoolean() {
        // Given, when
        Boolean message = true;

        // Then
        assertThat(formatter.format(message)).isEqualTo("primitive: true");
    }

    @Test
    public void shouldFormatByte() {
        // Given, when
        Byte message = 1;

        // Then
        assertThat(formatter.format(message)).isEqualTo("primitive: 1");
    }

    @Test
    public void shouldFormatInteger() {
        // Given, when
        Integer message = 2;

        // Then
        assertThat(formatter.format(message)).isEqualTo("primitive: 2");
    }

    @Test
    public void shouldFormatChar() {
        // Given, when
        Character message = 'a';

        // Then
        assertThat(formatter.format(message)).isEqualTo("char: a");
    }

    @Test
    public void shouldFormatString() {
        // Given, when
        String message = "abc";

        // Then
        assertThat(formatter.format(message)).isEqualTo("string: abc");
    }

    @Test
    public void shouldFormatIntArray() {
        // Given, when
        int[] message = new int[]{1, 2, 3};

        // Then
        assertThat(formatter.format(message)).isEqualTo("primitives array: [1, 2, 3]");
    }
}