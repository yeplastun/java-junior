package com.acme.edu.iteration02;

import com.acme.edu.LoggerFacade;
import com.acme.edu.SysoutCaptureAndAssertionAbility;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;

public class LoggerTest implements SysoutCaptureAndAssertionAbility {
    //region given
    @Before
    public void setUpSystemOut() throws IOException {
        resetOut();
        captureSysout();
    }

    @After
    public void tearDown() {
        resetOut();
    }
    //endregion

    @Test
    @Ignore
    public void shouldLogSequentIntegersAsSum() throws IOException {
        //region when
        LoggerFacade.log("str 1");
        LoggerFacade.log(1);
        LoggerFacade.log(2);
        LoggerFacade.log("str 2");
        LoggerFacade.log(0);
        LoggerFacade.flush();
        //endregion

        //region then
        assertSysoutEquals(
                "string: str 1" + System.lineSeparator() +
                        "primitive: 3" + System.lineSeparator() +
                        "string: str 2" + System.lineSeparator() +
                        "primitive: 0" + System.lineSeparator()
        );
        //endregion
    }

    @Test
    @Ignore
    public void shouldLogCorrectlyIntegerOverflowWhenSequentIntegers() {
        //region when
        LoggerFacade.log("str 1");
        LoggerFacade.log(10);
        LoggerFacade.log(Integer.MAX_VALUE);
        LoggerFacade.log("str 2");
        LoggerFacade.log(0);
        LoggerFacade.flush();
        //endregion

        //region then
        assertSysoutEquals(
                "string: str 1" + System.lineSeparator() +
                        "primitive: 10" + System.lineSeparator() +
                        "primitive: " + Integer.MAX_VALUE + System.lineSeparator() +
                        "string: str 2" + System.lineSeparator() +
                        "primitive: 0" + System.lineSeparator()
        );
        //endregion
    }

    @Test
    @Ignore
    public void shouldLogCorrectlyByteOverflowWhenSequentBytes() {
        //region when
        LoggerFacade.log("str 1");
        LoggerFacade.log((byte) 10);
        LoggerFacade.log((byte) Byte.MAX_VALUE);
        LoggerFacade.log("str 2");
        LoggerFacade.log(0);
        LoggerFacade.flush();
        //endregion

        //region then
        assertSysoutEquals(
                "string: str 1" + System.lineSeparator() +
                        "primitive: 10" + System.lineSeparator() +
                        "primitive: " + Byte.MAX_VALUE + System.lineSeparator() +
                        "string: str 2" + System.lineSeparator() +
                        "primitive: 0" + System.lineSeparator()
        );
        //endregion
    }

    @Test
    @Ignore
    public void shouldLogSameSubsequentStringsWithoutRepeat() throws IOException {
        //region when
        LoggerFacade.log("str 1");
        LoggerFacade.log("str 2");
        LoggerFacade.log("str 2");
        LoggerFacade.log(0);
        LoggerFacade.log("str 2");
        LoggerFacade.log("str 3");
        LoggerFacade.log("str 3");
        LoggerFacade.log("str 3");
        LoggerFacade.flush();
        //endregion

        //region then
        assertSysoutEquals(
                "string: str 1" + System.lineSeparator() +
                        "string: str 2 (x2)" + System.lineSeparator() +
                        "primitive: 0" + System.lineSeparator() +
                        "string: str 2" + System.lineSeparator() +
                        "string: str 3 (x3)" + System.lineSeparator()
        );
        //endregion
    }
}