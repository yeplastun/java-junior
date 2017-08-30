package com.acme.edu.custom;

import com.acme.edu.LogFormatter;
import com.acme.edu.Logger;
import com.acme.edu.LoggerFacade;
import com.acme.edu.TrivialLogFormatter;
import org.junit.Test;

import java.io.PrintStream;

import static org.junit.Assert.*;

public class LoggerTest {
    private final LogFormatter formatter = new TrivialLogFormatter();
    private final MockPrintStream stream = new MockPrintStream();
    private final Logger logger = new Logger(formatter, stream);

    @Test
    public void intWaitingForFlushTest() {
        logger.log(0);

        assertEquals("logger shouldn't write anything without flush", 0, stream.getStrings().size());
    }

    @Test
    public void charWaitingForFlushTest() {
        logger.log('a');

        assertEquals("logger shouldn't write anything without flush", 0, stream.getStrings().size());
    }

    @Test
    public void multipleIntegersWaitingForFlushTest() {
        logger.log(0);
        logger.log(1);
        logger.log(2);

        assertEquals("logger shouldn't write anything without flush", 0, stream.getStrings().size());
    }

    @Test
    public void bytePrintsAfterFlush() {
        logger.log(0xb0);
        logger.flush();

        assertEquals("loggers should print value after flush", 1, stream.getStrings().size());
    }

    @Test
    public void trivialFlushTest() {
        logger.flush();

        assertEquals("logger shouldn't print anything before logged", 0, stream.getStrings().size());
    }

    @Test
    public void repeatedFlushDoesNothing() {
        logger.log(0);
        logger.flush();
        logger.flush();
        logger.flush();
        logger.flush();
        logger.flush();

        assertEquals("subsequent flushes should do nothing", 1, stream.getStrings().size());
    }
}