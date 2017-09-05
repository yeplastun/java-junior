package com.acme.edu.rx.processor;

import io.reactivex.Observable;

import java.util.ArrayList;
import java.util.List;

/**
 * Provides business logic of processing for instances of {@link Integer}.
 */
public class IntegerLogProcessor {
    private IntegerLogProcessor() {
    }

    /**
     * Applies business logic for List of ints checking the overflow.
     *
     * @param integers Collection of sequential ints to process before logging.
     * @return Stream prepared for formatting.
     */
    public static Observable<Integer> process(List<Integer> integers) {
        List<Integer> result = new ArrayList<>();
        result.add(0);
        integers.forEach(x -> {
            int index = result.size() - 1;
            int value = result.get(index);
            if (Utils.isAdditionSafe(value, x)) {
                result.set(index, (value + x));
            } else {
                result.add(x);
            }
        });
        return Observable.fromIterable(result);
    }
}
