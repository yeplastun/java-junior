package com.acme.edu.iteration03;

import com.acme.edu.Logger;
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
    public void shouldLogIntegersArray() throws IOException {
        //region when
        LoggerFacade.log(new int[] {-1, 0, 1});
        LoggerFacade.flush();
        //endregion

        //region then
        assertSysoutEquals(
                "primitives array: {-1, 0, 1}" + lineSeparator()
        );
        //endregion
    }
}