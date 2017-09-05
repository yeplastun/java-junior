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
        int sum = 0;
        for (int x : integers) {
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

    private static boolean isAdditionSafe(int sum, int x) {
        final boolean positiveOverflow = x >= 0 || sum >= 0 || x + sum < 0;
        final boolean negativeOverflow = x < 0 || sum < 0 || x + sum >= 0;
        return positiveOverflow && negativeOverflow;
    }
}
