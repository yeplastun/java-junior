package com.acme.edu.rx.processor;

import io.reactivex.Observable;

import java.util.ArrayList;
import java.util.List;

public class ByteLogProcessor {
    public static Observable<Byte> process(List<Byte> integers) {
        List<Byte> result = new ArrayList<>();
        byte sum = 0;
        for (byte x : integers) {
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
        if (x < 0 && sum < 0 && (byte) (x + sum) >= 0) {
            return false;
        }
        if (x >= 0 && sum >= 0 && (byte) (x + sum) < 0) {
            return false;
        }
        return true;
    }
}
