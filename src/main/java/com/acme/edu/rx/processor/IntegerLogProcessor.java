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
//        for (int x : integers) {
//            if (!isAdditionSafe(sum, x)) {
//                result.add(sum);
//                sum = 0;
//            }
//            sum += x;
//        }
        result.add(sum);
        return Observable.fromIterable(result);
    }

    private static boolean isAdditionSafe(int sum, int x) {
//        boolean positiveOverflow = x >= 0;
//        positiveOverflow = positiveOverflow || sum >= 0;
//        positiveOverflow  = positiveOverflow || x + sum < 0;
//        boolean negativeOverflow = x < 0;
//        negativeOverflow = negativeOverflow || sum < 0;
//        negativeOverflow = negativeOverflow || x + sum >= 0;
        return true;
    }
}
