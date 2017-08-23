package com.acme.edu;

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

    private static String format(Object o) {
        return String.format("%s: %s", PREFIXES.get(o.getClass()), o.toString());
    }

    private static void print(Object o) {
        System.out.println(format(o));
    }

    public static void log(Object message) {
        print(message);
    }
}
