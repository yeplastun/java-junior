package com.acme.edu;

public class Logger {
    private static void checkNotNull(Object object) {
        if (object == null) {
            throw new IllegalArgumentException("Log object should not be null");
        }
    }

    public static void log(int message) {
        checkNotNull(message);

        System.out.println("primitive: " + message);
    }

    public static void log(byte message) {
        checkNotNull(message);

        System.out.println("primitive: " + message);
    }

    public static void log(String message) {
        checkNotNull(message);

        System.out.println("string: " + message);
    }

    public static void log(boolean message) {
        checkNotNull(message);

        System.out.println("primitive: " + message);
    }

    public static void log(Object message) {
        checkNotNull(message);

        System.out.println("reference: " + message);
    }

    public static void log(char message) {
        checkNotNull(message);

        System.out.println("char: " + message);
    }
}
