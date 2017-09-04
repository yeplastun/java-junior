package com.acme.edu.rx.processor;

import io.reactivex.Observable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class StringLogProcessor {
    private StringLogProcessor() { }

    public static Observable<String> process(List<String> messages) {
        List<String> result = new ArrayList<>();
        int counter = 1;
        String previous = messages.get(0);
        for (int i = 1; i < messages.size(); ++i) {
            if (Objects.equals(messages.get(i), previous)) {
                ++counter;
            } else {
                result.add(buildString(previous, counter));
                counter = 1;
                previous = messages.get(i);
            }
        }
        result.add(buildString(previous, counter));

        return Observable.fromIterable(result);
    }

    private static String buildString(String message, int counter) {
        return counter == 1 ? message : message + String.format(" (x%d)", counter);
    }
}
