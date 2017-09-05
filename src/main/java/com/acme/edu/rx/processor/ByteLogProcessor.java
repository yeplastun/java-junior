package com.acme.edu.rx.processor;

import io.reactivex.Observable;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides business logic of processing for instances of {@link Byte}.
 */
public class ByteLogProcessor {
    private ByteLogProcessor() {
    }

    /**
     * Applies business logic for List of bytes checking the overflow.
     *
     * @param bytes Collection of sequential bytes to process before logging.
     * @return Stream prepared for formatting.
     */
    public static Observable<Byte> process(List<Byte> bytes) {
        List<Byte> result = new ArrayList<>();
        result.add((byte) 0);
        bytes.forEach(x -> {
            int index = result.size() - 1;
            byte value = result.get(index);
            if (Utils.isAdditionSafe(value, x)) {
                result.set(index, (byte) (value + x));
            } else {
                result.add(x);
            }
        });
        return Observable.fromIterable(result);
    }

//    private static boolean isAdditionSafe(byte sum, byte x) {
//        int a = 1 - ((((int) sum) & 0xFF) >>> 7);
//        int b = 1 - ((((int) x) & 0xFF) >>> 7);
//        int c = (byte) (((x + sum) & 0xFF) >>> 7);
//        return ((a | b) ^ c) == 1;
//
////        int positiveOverflow = a | b | c;
////        int d = sum >>> 7;
////        int e = x >>> 7;
////        int f = 1 - (byte) ((byte) (x + sum) >>> 7);
////        int negativeOverflow = d | e | f;
////        final boolean positiveOverflow = x >= 0 || sum >= 0 || (byte) (x + sum) < 0;
////        final boolean negativeOverflow = x < 0 || sum < 0 || (byte) (x + sum) >= 0;
////        return (positiveOverflow & negativeOverflow) == 1;
//
//    }
}
