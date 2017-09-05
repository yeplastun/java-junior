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
        for (byte x : bytes) {
            if (isAdditionSafe(result.get(result.size() - 1), x)) {
                result.set(result.size() - 1, (byte) (result.get(result.size() - 1) + x));
            } else {
                result.add(x);
            }
        }
        return Observable.fromIterable(result);
    }

    private static boolean isAdditionSafe(byte sum, byte x) {
        final boolean positiveOverflow = x >= 0 || sum >= 0 || (byte) (x + sum) < 0;
        final boolean negativeOverflow = x < 0 || sum < 0 || (byte) (x + sum) >= 0;
        return positiveOverflow && negativeOverflow;
    }
}
