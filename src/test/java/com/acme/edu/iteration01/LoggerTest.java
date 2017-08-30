package com.acme.edu.iteration01;

import com.acme.edu.LoggerFacade;
import com.acme.edu.SysoutCaptureAndAssertionAbility;
import com.acme.edu.TrivialLogFormatter;
import org.junit.After;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.io.IOException;

import static java.lang.System.lineSeparator;

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
    public void shouldLogInteger() throws IOException {
        //region when
        LoggerFacade.log(1);
        LoggerFacade.flush();
        LoggerFacade.log(0);
        LoggerFacade.flush();
        LoggerFacade.log(-1);
        LoggerFacade.flush();
        //endregion

        //region then
        assertSysoutContains("primitive: ");
        assertSysoutEquals("primitive: 1" + lineSeparator() + "primitive: 0" + lineSeparator() + "primitive: -1" + lineSeparator());
        //endregion
    }

    @Test
    @Ignore
    public void shouldLogByte() throws IOException {
        //region when
        LoggerFacade.log((byte) 1);
        LoggerFacade.flush();
        LoggerFacade.log((byte) 0);
        LoggerFacade.flush();
        LoggerFacade.log((byte) -1);
        LoggerFacade.flush();
        //endregion

        //region then
        assertSysoutContains("primitive: ");
        assertSysoutContains("1");
        assertSysoutContains("0");
        assertSysoutContains("-1");
        //endregion
    }

    @Test
    @Ignore
    public void shouldLogChar() throws IOException {
        //region when
        LoggerFacade.log('a');
        LoggerFacade.flush();
        LoggerFacade.log('b');
        LoggerFacade.flush();
        //endregion

        //region then
        assertSysoutContains("char: ");
        assertSysoutContains("a");
        assertSysoutContains("b");
        //endregion
    }

    @Test
    @Ignore
    public void shouldLogString() throws IOException {
        //region when
        LoggerFacade.log("test string 1");
        LoggerFacade.flush();
        LoggerFacade.log("other str");
        LoggerFacade.flush();
        //endregion

        //region then
        assertSysoutContains("string: ");
        assertSysoutContains("test string 1");
        assertSysoutContains("other str");
        //endregion
    }

    @Test
    @Ignore
    public void shouldLogBoolean() throws IOException {
        //region when
        LoggerFacade.log(true);
        LoggerFacade.flush();
        LoggerFacade.log(false);
        LoggerFacade.flush();
        //endregion

        //region then
        assertSysoutContains("primitive: ");
        assertSysoutContains("true");
        assertSysoutContains("false");
        //endregion
    }

    @Test
    @Ignore
    public void shouldLogReference() throws IOException {
        //region when
        LoggerFacade.log(new Object());
        LoggerFacade.flush();
        //endregion

        //region then
        assertSysoutContains("reference: ");
        assertSysoutContains("@");
        //endregion
    }

}