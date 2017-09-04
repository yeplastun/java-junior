package com.acme.edu.rx;

import com.acme.edu.rx.exception.InvalidLogMessageException;
import com.acme.edu.rx.exception.LogMessageException;
import com.acme.edu.rx.formatter.TestLogFormatter;
import com.acme.edu.rx.saver.ConsoleLogSaver;
import com.acme.edu.rx.saver.ExceptionTestSaver;
import io.reactivex.observers.TestObserver;
import org.junit.Before;
import org.junit.Test;

import java.util.Objects;

public class RxLoggerExceptionTest {
    private TestObserver<LogMessageException> exceptionObserver;

    @Before
    public void setUpSubscriber() {
        exceptionObserver = new TestObserver<>();
    }

    @Test
    public void shouldThrowIOLogMessageException() {
        // Given
        RxLogger logger = new RxLogger(new TestLogFormatter(), new ExceptionTestSaver());
        logger.getExceptionStream().subscribe(exceptionObserver);

        // When
        logger.log(1);
        logger.flush();

        // Then
        exceptionObserver.assertValue(ExceptionTestSaver.getInstance());
    }

    @Test
    public void shouldThrowInvalidLogMessageException() {
        // Given
        RxLogger logger = new RxLogger(new TestLogFormatter(), new ConsoleLogSaver());
        logger.getExceptionStream().subscribe(exceptionObserver);

        // When
        logger.log("");
        logger.flush();

        // Then
        exceptionObserver.assertValue(ex -> ex.getClass() == InvalidLogMessageException.class
                && Objects.equals(ex.getLogMessage(), ""));
    }

    @Test(expected = IllegalArgumentException.class)
    @SuppressWarnings("ConstantConditions")
    public void shouldThrowIllegalArgumentException() {
        // Given
        RxLogger logger = new RxLogger(new TestLogFormatter(), new ConsoleLogSaver());

        // When, then
        logger.log(null);
    }
}
