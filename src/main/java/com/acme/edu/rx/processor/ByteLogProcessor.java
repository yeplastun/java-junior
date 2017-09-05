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
            if (Utils.isAdditionSafe(value << 24, x << 24)) {
                result.set(index, (byte) (value + x));
            } else {
                result.add(x);
            }
        });
        return Observable.fromIterable(result);
    }
}
