package com.acme.edu.rx;

import com.acme.edu.rx.formatter.TestLogFormatter;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.runners.MockitoJUnitRunner;

import java.io.PrintStream;
import java.util.Arrays;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RxLoggerTest {
    private RxLogger logger;

    @Spy
    private TestLogFormatter formatter;
    @Mock
    private PrintStream outputStream;

    @Before
    public void setUpLogger() {
        logger = new RxLogger(formatter, outputStream);
    }

    @Test
    @SuppressWarnings("ConstantConditions")
    public void shouldLogAllTypes() {
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

        verify(outputStream).println(objectMessage.toString());
        verify(outputStream).println(booleanMessage.toString());
        verify(outputStream).println(byteMessage.toString());
        verify(outputStream).println(integerMessage.toString());
        verify(outputStream).println(charMessage.toString());
        verify(outputStream).println(stringMessage);
        verify(outputStream).println(Arrays.toString(intArrayMessage));
    }

    @Test
    public void shouldNotAccumulateObjects() {
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

        verify(outputStream).println(message1.toString());
        verify(outputStream).println(message2.toString());
    }

    @SuppressWarnings("ConstantConditions")
    @Test
    public void shouldNotAccumulateBooleans() {
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

        verify(outputStream).println(message1.toString());
        verify(outputStream).println(message2.toString());
    }

    @Test
    public void shouldNotAccumulateChars() {
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

        verify(outputStream).println(message1.toString());
        verify(outputStream).println(message2.toString());
    }

    @Test
    public void shouldNotAccumulateIntegerArrays() {
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

        verify(outputStream).println(Arrays.toString(message1));
        verify(outputStream).println(Arrays.toString(message2));
    }

    @Test
    public void shouldStopByteSumOnOtherMessage() {
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

        verify(outputStream, times(2)).println("3");
        verify(outputStream).println(objectMessage.toString());
    }

    @Test
    public void shouldStopIntegerSumOnOtherMessage() {
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

        verify(outputStream, times(2)).println("3");
        verify(outputStream).println(objectMessage.toString());
    }

    @Test
    public void shouldStopStringAccumulationOnOtherMessage() {
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

        verify(outputStream).println("start (x2)");
        verify(outputStream).println(objectMessage.toString());
        verify(outputStream).println("finish");
    }

    @Test
    public void shouldStopStringAccumulationOnOtherString() {
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

        verify(outputStream).println("start (x2)");
        verify(outputStream).println("test");
        verify(outputStream).println("finish");
    }

    @Test
    public void shouldAccumulateSequelStringMessages() {
        // Given, when
        logger.log("start");
        logger.log("start");
        logger.log("finish");
        logger.log("finish");
        logger.flush();

        // Then
        verify(formatter).format("start (x2)");
        verify(formatter).format("finish (x2)");

        verify(outputStream).println("start (x2)");
        verify(outputStream).println("finish (x2)");
    }

    @Test
    public void shouldStopByteSumOnFlush() {
        // Given, when
        logger.log((byte) 1);
        logger.log((byte) 2);
        logger.flush();
        logger.log((byte) 1);
        logger.flush();

        // Then
        verify(formatter).format((byte) 3);
        verify(formatter).format((byte) 1);

        verify(outputStream).println("3");
        verify(outputStream).println("1");
    }

    @Test
    public void shouldStopIntegerSumOnFlush() {
        // Given, when
        logger.log(1);
        logger.log(2);
        logger.flush();
        logger.log(1);
        logger.flush();

        // Then
        verify(formatter).format(3);
        verify(formatter).format(1);

        verify(outputStream).println("3");
        verify(outputStream).println("1");
    }

    @Test
    public void shouldStopStringAccumulationOnFlush() {
        // Given, when
        logger.log("start");
        logger.log("start");
        logger.flush();
        logger.log("finish");
        logger.flush();

        // Then
        verify(formatter).format("start (x2)");
        verify(formatter).format("finish");

        verify(outputStream).println("start (x2)");
        verify(outputStream).println("finish");
    }

    @Test
    public void shouldLogCorrectlyByteOverflowScenarios() {
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
    public void shouldLogCorrectlyIntegerOverflowScenarios() {
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