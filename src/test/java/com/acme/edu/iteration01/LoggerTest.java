package com.acme.edu.iteration01;

import com.acme.edu.LoggerFacade;
import com.acme.edu.SysoutCaptureAndAssertionAbility;
import org.junit.After;
import org.junit.Before;
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
    public void shouldLogInteger() throws IOException {
        //region when
        LoggerFacade.log(1);
        LoggerFacade.log(0);
        LoggerFacade.log(-1);
        LoggerFacade.flush();
        //endregion

        //region then
        assertSysoutContains("primitive: 0");
        //endregion
    }

    @Test
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