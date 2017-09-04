package com.acme.edu.rx.saver;

import com.acme.edu.rx.exception.IOLogMessageException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static java.lang.System.lineSeparator;
import static org.fest.assertions.Assertions.assertThat;

public class ConsoleLogSaverTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();

    @Before
    public void setUpStream() {
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void cleanUpStream() {
        System.setOut(null);
    }

    @Test
    public void shouldSaveToConsole() throws IOLogMessageException {
        // Given
        LogSaver saver = new ConsoleLogSaver();
        String testMessage = "test message";

        // When
        saver.save(testMessage);

        // Then
        assertThat(outContent.toString()).isEqualTo(testMessage + lineSeparator());
    }
}