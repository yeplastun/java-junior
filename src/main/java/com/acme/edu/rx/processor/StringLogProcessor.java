package com.acme.edu.rx.processor;

import com.acme.edu.rx.exception.InvalidLogMessageException;
import io.reactivex.Observable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Provides business logic of processing for instances of {@link String}.
 */
public class StringLogProcessor {
    private StringLogProcessor() {
    }

    /**
     * Applies business logic for List of strings checking the subsequent equal strings.
     *
     * @param strings Collection of sequential ints to process before logging.
     * @return Stream prepared for formatting.
     */
    public static Observable<String> process(List<String> strings) throws InvalidLogMessageException {
        if (strings.stream().filter(String::isEmpty).count() > 0) {
            throw new InvalidLogMessageException("String message should not be null", "");
        }

        List<String> result = new ArrayList<>();
        int counter = 1;
        String previous = strings.get(0);
        for (int i = 1; i < strings.size(); ++i) {
            if (Objects.equals(strings.get(i), previous)) {
                ++counter;
            } else {
                result.add(buildString(previous, counter));
                counter = 1;
                previous = strings.get(i);
            }
        }
        result.add(buildString(previous, counter));

        return Observable.fromIterable(result);
    }

    private static String buildString(String message, int counter) {
        return counter == 1 ? message : message + String.format(" (x%d)", counter);
    }
}
