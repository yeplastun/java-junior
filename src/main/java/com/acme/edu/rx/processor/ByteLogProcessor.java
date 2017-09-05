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
        byte sum = 0;
        for (byte x : bytes) {
            if (!isAdditionSafe(sum, x)) {
                result.add(sum);
                sum = x;
            } else {
                sum += x;
            }
        }
        result.add(sum);
        return Observable.fromIterable(result);
    }

    private static boolean isAdditionSafe(byte sum, byte x) {
        final boolean positiveOverflow = x >= 0 || sum >= 0 || (byte) (x + sum) < 0;
        final boolean negativeOverflow = x < 0 || sum < 0 || (byte) (x + sum) >= 0;
        return positiveOverflow && negativeOverflow;
    }
}
