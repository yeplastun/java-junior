package com.acme.edu.rx.processor;

import io.reactivex.Observable;

import java.util.ArrayList;
import java.util.List;

public class IntegerLogProcessor {
    private IntegerLogProcessor() { }

    public static Observable<Integer> process(List<Integer> messages) {
        List<Integer> result = new ArrayList<>();
        int sum = 0;
        for (int x : messages) {
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
