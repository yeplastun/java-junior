package com.acme.edu.rx;

import com.acme.edu.rx.exception.IOLogMessageException;
import com.acme.edu.rx.exception.InvalidLogMessageException;
import com.acme.edu.rx.exception.LogMessageException;
import com.acme.edu.rx.formatter.TestLogFormatter;
import com.acme.edu.rx.saver.ConsoleLogSaver;
import com.acme.edu.rx.saver.ExceptionTestSaver;
import com.sun.javaws.exceptions.InvalidArgumentException;
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
    public void shouldThrowIOLogMessageException() throws InvalidLogMessageException {
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
    public void shouldThrowInvalidLogMessageException() throws InvalidLogMessageException {
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

    @Test(expected = InvalidLogMessageException.class)
    @SuppressWarnings("ConstantConditions")
    public void shouldThrowIllegalArgumentException() throws InvalidLogMessageException {
        // Given
        RxLogger logger = new RxLogger(new TestLogFormatter(), new ExceptionTestSaver());

        // When, then
        logger.log(null);
    }

    @Test
    public void shouldTrowSeveralExceptions() throws InvalidLogMessageException {
        // Given
        RxLogger logger = new RxLogger(new TestLogFormatter(), new ExceptionTestSaver());
        logger.getExceptionStream().subscribe(exceptionObserver);
        // When
        logger.log(1);
        logger.flush();
        logger.log("");
        logger.flush();

        // Then
        exceptionObserver.assertValueAt(0, ex -> ex.getClass() == IOLogMessageException.class);
        exceptionObserver.assertValueAt(1, ex -> ex.getClass() == InvalidLogMessageException.class);
    }
}
