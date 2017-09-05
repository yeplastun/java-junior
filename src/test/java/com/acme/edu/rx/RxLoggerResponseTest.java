package com.acme.edu.rx;

import com.acme.edu.rx.exception.IOLogMessageException;
import com.acme.edu.rx.exception.InvalidLogMessageException;
import com.acme.edu.rx.formatter.TestLogFormatter;
import com.acme.edu.rx.response.LogResponse;
import com.acme.edu.rx.saver.ConsoleLogSaver;
import com.acme.edu.rx.saver.ExceptionTestSaver;
import com.acme.edu.rx.saver.LogSaver;
import io.reactivex.observers.TestObserver;
import io.reactivex.subscribers.TestSubscriber;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.Objects;

import static org.mockito.Mockito.mock;

public class RxLoggerResponseTest {
    private TestObserver<LogResponse> exceptionObserver;

    @Before
    public void setUpSubscriber() {
        exceptionObserver = new TestObserver<>();
    }

    @Test
    public void shouldThrowIOLogMessageException() throws InvalidLogMessageException {
        // Given
        RxLogger logger = new RxLogger(new TestLogFormatter(), new ExceptionTestSaver());
        logger.getResponseStream().subscribe(exceptionObserver);

        // When
        logger.log(1);
        logger.flush();

        // Then
        exceptionObserver.assertValue(response ->
            response.getException().getClass() == IOLogMessageException.class
                && Objects.equals(response.getException().getLogMessage(), "test log message")
        );
    }

    @Test
    public void shouldThrowInvalidLogMessageException() throws InvalidLogMessageException {
        // Given
        RxLogger logger = new RxLogger(new TestLogFormatter(), new ConsoleLogSaver());
        logger.getResponseStream().subscribe(exceptionObserver);

        // When
        logger.log("");
        logger.flush();

        // Then
        exceptionObserver.assertValue(response ->
            response.getException().getClass() == InvalidLogMessageException.class
                && Objects.equals(response.getLogMessage(), "")
                && !response.isSuccessful());
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
        logger.getResponseStream().subscribe(exceptionObserver);

        // When
        logger.log(1);
        logger.flush();
        logger.log("");
        logger.flush();

        // Then
        exceptionObserver.assertValueAt(0,
            response ->
                response.getException().getClass() == IOLogMessageException.class
                    && !response.isSuccessful());
        exceptionObserver.assertValueAt(1, response ->
            response.getException().getClass() == InvalidLogMessageException.class
                && !response.isSuccessful());
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    public void shouldEmitLogResponseOnSuccessfulLog() throws InvalidLogMessageException {
        // Given
        RxLogger logger = new RxLogger(new TestLogFormatter(), mock(LogSaver.class));
        TestSubscriber<LogResponse> responseSubscriber = new TestSubscriber<>();
        logger.getResponseStream().subscribe(responseSubscriber::onNext);

        Object objectMessage = new Object();
        Boolean booleanMessage = true;
        Byte byteMessage = 1;
        Integer integerMessage = 2;
        Character charMessage = 'a';
        String stringMessage = "abc";
        int[] intArrayMessage = new int[]{1, 2, 3};

        // When
        logger.log(objectMessage);
        logger.log(booleanMessage);
        logger.log(byteMessage);
        logger.log(integerMessage);
        logger.log(charMessage);
        logger.log(stringMessage);
        logger.log(intArrayMessage);
        logger.flush();

        // Then
        responseSubscriber.assertValueAt(0, response ->
            Objects.equals(response.getLogMessage(), objectMessage.toString())
        );
        responseSubscriber.assertValueAt(1, response ->
            Objects.equals(response.getLogMessage(), booleanMessage.toString())
        );
        responseSubscriber.assertValueAt(2, response ->
            Objects.equals(response.getLogMessage(), byteMessage.toString())
        );
        responseSubscriber.assertValueAt(3, response ->
            Objects.equals(response.getLogMessage(), integerMessage.toString())
        );
        responseSubscriber.assertValueAt(4, response ->
            Objects.equals(response.getLogMessage(), charMessage.toString())
        );
        responseSubscriber.assertValueAt(5, response ->
            Objects.equals(response.getLogMessage(), stringMessage)
        );
        responseSubscriber.assertValueAt(6, response ->
            Objects.equals(response.getLogMessage(), Arrays.toString(intArrayMessage))
        );

    }
}
