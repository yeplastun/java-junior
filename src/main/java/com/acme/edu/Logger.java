package com.acme.edu;

import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Logger {
    private static final Map<Class, String> PREFIXES;

    static {
        Map<Class, String> tempMap = new HashMap<>();
        tempMap.put(Object.class, "reference");
        tempMap.put(String.class, "string");
        tempMap.put(Character.class, "char");
        tempMap.put(Byte.class, "primitive");
        tempMap.put(Short.class, "primitive");
        tempMap.put(Integer.class, "primitive");
        tempMap.put(Long.class, "primitive");
        tempMap.put(Boolean.class, "primitive");
        tempMap.put(Float.class, "primitive");
        tempMap.put(Double.class, "primitive");
        PREFIXES = Collections.unmodifiableMap(tempMap);
    }

    private static String format(@NotNull Object o) {
        return String.format("%s: %s" + System.lineSeparator(), PREFIXES.get(o.getClass()), o.toString());
    }

    private static void print(@NotNull Object o) {
        System.out.print(format(o));
    }

    public static void log(Object message) {
        if (message == null) {
            throw new IllegalArgumentException("loggable message mustn't be null");
        }
        print(message);
    }
}
