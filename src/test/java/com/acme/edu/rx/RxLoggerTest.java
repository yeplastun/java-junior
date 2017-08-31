package com.acme.edu.rx;

import com.acme.edu.SysoutCaptureAndAssertionAbility;
import org.junit.Before;
import org.junit.Test;

import static java.lang.System.lineSeparator;

public class RxLoggerTest implements SysoutCaptureAndAssertionAbility {
    private RxLogger logger;

    @Before
    public void setUpSysoutCapturing() {
        resetOut();
        captureSysout();
    }

    @Before
    public void setUpLogger() {
        logger = new RxLogger();
    }

    @Test
    public void shouldLogSumOfIntegers() {
        // Given, when
        logger.log("start");
        logger.log(1);
        logger.log(2);
        logger.log("finish");

        // Then
        assertSysoutEquals(
                "string: start" + lineSeparator()
                        + "primitive: 3" + lineSeparator()
                        + "string: finish" + lineSeparator()
        );
    }
}