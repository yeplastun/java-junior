package com.acme.edu.rx;

import com.acme.edu.rx.exception.LoggingException;
import com.acme.edu.rx.formatter.TestLogFormatter;
import com.acme.edu.rx.saver.LogSaver;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RxLoggerTest {
    private RxLogger logger;

    @Spy
    private TestLogFormatter formatter;
    @Mock
    private LogSaver logSaver;

    @Before
    public void setUpLogger() {
        logger = new RxLogger(formatter, logSaver);
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    public void shouldLogAllTypes() throws LoggingException {
        // Given
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
        verify(formatter).format(objectMessage);
        verify(formatter).format(booleanMessage);
        verify(formatter).format(byteMessage);
        verify(formatter).format(integerMessage);
        verify(formatter).format(charMessage);
        verify(formatter).format(stringMessage);
        verify(formatter).format(intArrayMessage);

        verify(logSaver).save(objectMessage.toString());
        verify(logSaver).save(booleanMessage.toString());
        verify(logSaver).save(byteMessage.toString());
        verify(logSaver).save(integerMessage.toString());
        verify(logSaver).save(charMessage.toString());
        verify(logSaver).save(stringMessage);
        verify(logSaver).save(Arrays.toString(intArrayMessage));
    }

    @Test
    public void shouldNotAccumulateObjects() throws LoggingException {
        // Given
        Object message1 = new Object();
        Object message2 = new Object();

        // When
        logger.log(message1);
        logger.log(message2);
        logger.flush();

        // Then
        verify(formatter).format(message1);
        verify(formatter).format(message2);

        verify(logSaver).save(message1.toString());
        verify(logSaver).save(message2.toString());
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    public void shouldNotAccumulateBooleans() throws LoggingException {
        // Given
        Boolean message1 = true;
        Boolean message2 = false;

        // When
        logger.log(message1);
        logger.log(message2);
        logger.flush();

        // Then
        verify(formatter).format(message1);
        verify(formatter).format(message2);

        verify(logSaver).save(message1.toString());
        verify(logSaver).save(message2.toString());
    }

    @Test
    public void shouldNotAccumulateChars() throws LoggingException {
        // Given
        Character message1 = 'a';
        Character message2 = 'b';

        // When
        logger.log(message1);
        logger.log(message2);
        logger.flush();

        // Then
        verify(formatter).format(message1);
        verify(formatter).format(message2);

        verify(logSaver).save(message1.toString());
        verify(logSaver).save(message2.toString());
    }

    @Test
    public void shouldNotAccumulateIntegerArrays() throws LoggingException {
        // Given
        int[] message1 = new int[]{1, 2, 3};
        int[] message2 = new int[]{4, 5, 6};

        // When
        logger.log(message1);
        logger.log(message2);
        logger.flush();

        // Then
        verify(formatter).format(message1);
        verify(formatter).format(message2);

        verify(logSaver).save(Arrays.toString(message1));
        verify(logSaver).save(Arrays.toString(message2));
    }

    @Test
    public void shouldStopByteSumOnOtherMessage() throws LoggingException {
        // Given
        final Object objectMessage = new Object();

        // When
        logger.log((byte) 1);
        logger.log((byte) 2);
        logger.log(objectMessage);
        logger.log((byte) 1);
        logger.log((byte) 2);
        logger.flush();

        // Then
        verify(formatter, times(2)).format((byte) 3);
        verify(formatter).format(objectMessage);

        verify(logSaver, times(2)).save("3");
        verify(logSaver).save(objectMessage.toString());
    }

    @Test
    public void shouldStopIntegerSumOnOtherMessage() throws LoggingException {
        // Given
        final Object objectMessage = new Object();

        // When
        logger.log(1);
        logger.log(2);
        logger.log(objectMessage);
        logger.log(1);
        logger.log(2);
        logger.flush();

        // Then
        verify(formatter, times(2)).format(3);
        verify(formatter).format(objectMessage);

        verify(logSaver, times(2)).save("3");
        verify(logSaver).save(objectMessage.toString());
    }

    @Test
    public void shouldStopStringAccumulationOnOtherMessage() throws LoggingException {
        // Given
        final Object objectMessage = new Object();

        // When
        logger.log("start");
        logger.log("start");
        logger.log(objectMessage);
        logger.log("finish");
        logger.flush();

        // Then
        verify(formatter).format("start (x2)");
        verify(formatter).format(objectMessage);
        verify(formatter).format("finish");

        verify(logSaver).save("start (x2)");
        verify(logSaver).save(objectMessage.toString());
        verify(logSaver).save("finish");
    }

    @Test
    public void shouldStopStringAccumulationOnOtherString() throws LoggingException {
        // Given, when
        logger.log("start");
        logger.log("start");
        logger.log("test");
        logger.log("finish");
        logger.flush();

        // Then
        verify(formatter).format("start (x2)");
        verify(formatter).format("test");
        verify(formatter).format("finish");

        verify(logSaver).save("start (x2)");
        verify(logSaver).save("test");
        verify(logSaver).save("finish");
    }

    @Test
    public void shouldAccumulateSequelStringMessages() throws LoggingException {
        // Given, when
        logger.log("start");
        logger.log("start");
        logger.log("finish");
        logger.log("finish");
        logger.flush();

        // Then
        verify(formatter).format("start (x2)");
        verify(formatter).format("finish (x2)");

        verify(logSaver).save("start (x2)");
        verify(logSaver).save("finish (x2)");
    }

    @Test
    public void shouldStopByteSumOnFlush() throws LoggingException {
        // Given, when
        logger.log((byte) 1);
        logger.log((byte) 2);
        logger.flush();
        logger.log((byte) 1);
        logger.flush();

        // Then
        verify(formatter).format((byte) 3);
        verify(formatter).format((byte) 1);

        verify(logSaver).save("3");
        verify(logSaver).save("1");
    }

    @Test
    public void shouldStopIntegerSumOnFlush() throws LoggingException {
        // Given, when
        logger.log(1);
        logger.log(2);
        logger.flush();
        logger.log(1);
        logger.flush();

        // Then
        verify(formatter).format(3);
        verify(formatter).format(1);

        verify(logSaver).save("3");
        verify(logSaver).save("1");
    }

    @Test
    public void shouldStopStringAccumulationOnFlush() throws LoggingException {
        // Given, when
        logger.log("start");
        logger.log("start");
        logger.flush();
        logger.log("finish");
        logger.flush();

        // Then
        verify(formatter).format("start (x2)");
        verify(formatter).format("finish");

        verify(logSaver).save("start (x2)");
        verify(logSaver).save("finish");
    }

    @Test
    public void shouldLogCorrectlyByteOverflowScenarios() throws LoggingException {
        // Given, when
        logger.log((byte) 64);
        logger.log((byte) 63);
        logger.flush();
        logger.log((byte) 64);
        logger.log((byte) 64);
        logger.flush();
        logger.log((byte) 64);
        logger.log((byte) -64);
        logger.flush();
        logger.log((byte) 64);
        logger.log((byte) -65);

        logger.flush();
        logger.log((byte) -64);
        logger.log((byte) -64);
        logger.flush();
        logger.log((byte) -64);
        logger.log((byte) 63);
        logger.flush();
        logger.log((byte) -64);
        logger.log((byte) 64);
        logger.flush();
        logger.log((byte) -64);
        logger.log((byte) -65);
        logger.flush();

        // Then
        InOrder order = inOrder(formatter);
        order.verify(formatter).format((byte) 127);
        order.verify(formatter, times(2)).format((byte) 64);
        order.verify(formatter).format((byte) 0);
        order.verify(formatter).format((byte) -1);
        order.verify(formatter).format((byte) -128);
        order.verify(formatter).format((byte) -1);
        order.verify(formatter).format((byte) 0);
        order.verify(formatter).format((byte) -64);
        order.verify(formatter).format((byte) -65);
    }

    @Test
    public void shouldLogCorrectlyIntegerOverflowScenarios() throws LoggingException {
        //Given
        int bigPositive = Integer.MAX_VALUE / 2 + 1;
        int bigNegative = Integer.MIN_VALUE / 2;

        // When
        logger.log(bigPositive);
        logger.log(bigPositive - 1);
        logger.flush();
        logger.log(bigPositive);
        logger.log(bigPositive);
        logger.flush();
        logger.log(bigPositive);
        logger.log(-bigPositive);
        logger.flush();
        logger.log(bigPositive);
        logger.log(-bigPositive - 1);
        logger.flush();

        logger.log(bigNegative);
        logger.log(bigNegative);
        logger.flush();
        logger.log(bigNegative);
        logger.log(-bigNegative - 1);
        logger.flush();
        logger.log(bigNegative);
        logger.log(-bigNegative);
        logger.flush();
        logger.log(bigNegative);
        logger.log(bigNegative - 1);
        logger.flush();

        // Then
        InOrder order = inOrder(formatter);
        order.verify(formatter).format(Integer.MAX_VALUE);
        order.verify(formatter, times(2)).format(bigPositive);
        order.verify(formatter).format(0);
        order.verify(formatter).format(-1);
        order.verify(formatter).format(Integer.MIN_VALUE);
        order.verify(formatter).format(-1);
        order.verify(formatter).format(0);
        order.verify(formatter).format(bigNegative);
        order.verify(formatter).format(bigNegative - 1);
    }
}