package com.acme.edu.rx.processor;

/**
 * Utility class with useful methods for processors
 */
class Utils {
    static boolean isAdditionSafe(int a, int b) {
        int condition1 = a >>> 31;
        int condition2 = b >>> 31;
        int condition3 = 1 - ((a + b) >>> 31);
        return ((condition1 | condition2 | condition3) & ((1 - condition1) | (1 - condition2) | (1 - condition3))) == 1;
    }

    static boolean isAdditionSafe(byte a, byte b) {
        return isAdditionSafe(a << 24, b << 24);
    }
}
