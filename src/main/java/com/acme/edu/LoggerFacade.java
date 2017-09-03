package com.acme.edu;

import com.acme.edu.rx.RxLogger;

public class LoggerFacade {
    private static final RxLogger LOGGER = new RxLogger();

    public static void log(Object o) {
        LOGGER.log(o);
    }

    public static void flush() {
        LOGGER.flush();
    }
}
