package com.acme.edu;

public class LoggerFacade {
    private static final Logger LOGGER = new Logger(new TrivialLogFormatter(), System.out);

    public static void log(Object o) {
        LOGGER.log(o);
    }

    public static void flush() {
        LOGGER.flush();
    }
}
