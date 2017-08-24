package com.acme.edu;

import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

class Utility {
    static final Map<Class, Boolean> NEED_TO_COLLECT;
    static final Map<Class, Consumer<Object>> COLLECTORS;
    static final Map<Class, Function<Object, String>> FORMATTERS;
    private static final Map<Class, String> PREFIXES;

    static {
        Map<Class, String> tempMap = new HashMap<>();
        tempMap.put(Object.class, "reference");
        tempMap.put(String.class, "string");
        tempMap.put(Character.class, "char");
        tempMap.put(Byte.class, "primitive");
        tempMap.put(Integer.class, "primitive");
        tempMap.put(Boolean.class, "primitive");
        PREFIXES = Collections.unmodifiableMap(tempMap);
    }

    static {
        Map<Class, Boolean> tempMap = new HashMap<>();
        tempMap.put(Object.class, Boolean.FALSE);
        tempMap.put(String.class, Boolean.TRUE);
        tempMap.put(Character.class, Boolean.FALSE);
        tempMap.put(Byte.class, Boolean.TRUE);
        tempMap.put(Integer.class, Boolean.TRUE);
        tempMap.put(Boolean.class, Boolean.FALSE);
        NEED_TO_COLLECT = Collections.unmodifiableMap(tempMap);
    }

    static {
        Map<Class, Consumer<Object>> tempMap = new HashMap<>();
        tempMap.put(Object.class, Utility::defaultCollector);
        tempMap.put(String.class, Utility::stringCollector);
        tempMap.put(Byte.class, Utility::byteCollector);
        tempMap.put(Integer.class, Utility::intCollector);
        COLLECTORS = Collections.unmodifiableMap(tempMap);
    }

    static {
        Map<Class, Function<Object, String>> tempMap = new HashMap<>();
        tempMap.put(Object.class, Utility::defaultFormatter);
        tempMap.put(String.class, Utility::stringFormatter);
        FORMATTERS = Collections.unmodifiableMap(tempMap);
    }

    static <V> V getObjectFromMapOrDefault(Class clazz, Map<Class, V> map) {
        V value = map.get(clazz);
        return value == null ? map.get(Object.class) : value;
    }

    private static boolean isAdditionSafe(int a, byte b) {
        return a <= 0 || b <= 0 || (byte) a + b >= 0;
    }

    private static boolean isAdditionSafe(int a, int b) {
        return a <= 0 || b <= 0 || a + b >= 0;
    }

    private static void defaultCollector(Object o) {

    }

    static void print() {
        System.out.println(getObjectFromMapOrDefault(State.previousClass, FORMATTERS).apply(
                State.previousInstance));
        State.sum = 0;
    }

    private static void stringCollector(@NotNull Object o) {
        if (o.equals(State.previousInstance)) {
            ++State.stringCounter;
            return;
        }

        print();
        State.previousInstance = o;
        State.stringCounter = 0;
    }

    private static void byteCollector(@NotNull Object o) {
        byte b = (byte) o;
        if (isAdditionSafe(State.sum, b)) {
            State.sum += b;
        }
    }

    private static void intCollector(@NotNull Object o) {
        int x = (int) o;
        if (isAdditionSafe(State.sum, x)) {
            State.sum += x;
        }
    }

    private static String defaultFormatter(@NotNull Object o) {
        return String.format("%s: %s", getObjectFromMapOrDefault(o.getClass(), PREFIXES), o);
    }

    private static String stringFormatter(@NotNull Object o) {
        return State.stringCounter > 0 ?
                String.format("%s: %s (x%d)", getObjectFromMapOrDefault(String.class, PREFIXES), o, ++State.stringCounter)
                : String.format("%s: %s", getObjectFromMapOrDefault(String.class, PREFIXES), o);
    }
}