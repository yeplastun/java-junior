package com.acme.edu;

import java.util.HashMap;
import java.util.Map;

public class Logger {
    private static Map<Class, String> prefixes = new HashMap<>();

    static {
        prefixes.put(Object.class, "reference");
        prefixes.put(String.class, "string");
        prefixes.put(Character.class, "char");
        prefixes.put(Byte.class, "primitive");
        prefixes.put(Short.class, "primitive");
        prefixes.put(Integer.class, "primitive");
        prefixes.put(Long.class, "primitive");
        prefixes.put(Boolean.class, "primitive");
        prefixes.put(Float.class, "primitive");
        prefixes.put(Double.class, "primitive");
    }

    private static String format(Object o) {
        return String.format("%s: %s", prefixes.get(o.getClass()), o.toString());
    }

    private static void print(Object o) {
        System.out.println(format(o));
    }

    public static void log(Object message) {
        print(message);
    }
}
