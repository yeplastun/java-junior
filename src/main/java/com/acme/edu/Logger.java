package com.acme.edu;

import static com.acme.edu.Utility.*;

public class Logger {
    public static void log(Object message) {
        if (message == null) {
            throw new IllegalArgumentException("loggable message mustn't be null");
        }

        Class clazz = message.getClass();
        if (State.previousClass == null) {
            getObjectFromMapOrDefault(clazz, INITIALIZERS).accept(message);
            saveState(message, clazz);
            return;
        }

        final boolean needToCollect = clazz.equals(State.previousClass) && getObjectFromMapOrDefault(clazz, NEED_TO_COLLECT);
        if (needToCollect) {
            getObjectFromMapOrDefault(clazz, COLLECTORS).accept(message);
            return;
        }

        Utility.print();
        getObjectFromMapOrDefault(clazz, INITIALIZERS).accept(message);
        saveState(message, clazz);
    }

    private static void saveState(Object message, Class clazz) {
        State.previousClass = clazz;
        State.previousInstance = message;
    }

    public static void flush() {
        if (State.previousClass == null) {
            return;
        }
        Utility.print();
        saveState(null, null);
    }
}