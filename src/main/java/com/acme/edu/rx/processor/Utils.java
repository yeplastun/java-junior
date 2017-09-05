package com.acme.edu.rx.processor;

/**
 * Utility class with useful methods for processors
 */
class Utils {
    private Utils() {
    }

    /**
     * Checks sum of two numbers for integer overflow.
     * @param a First addend.
     * @param b Second addend.
     * @return True if there's not overflow or underflow.
     */
    static boolean isAdditionSafe(int a, int b) {
        int condition1 = a >>> 31;
        int condition2 = b >>> 31;
        int condition3 = (a + b) >>> 31;
        return ((condition1 | condition2 | (1 - condition3)) & ((1 - condition1) | (1 - condition2) | condition3)) == 1;
    }
}
