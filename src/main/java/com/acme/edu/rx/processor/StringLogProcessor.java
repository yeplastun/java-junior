package com.acme.edu.rx.processor;

import com.acme.edu.rx.exception.InvalidLogMessageException;
import io.reactivex.Observable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;

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

        List<Integer> counters = new ArrayList<>();
        List<String> messages = new ArrayList<>();
        List<String> result = new ArrayList<>();
        counters.add(0);
        messages.add(strings.get(0));
        strings.forEach(current -> {
            int index = counters.size() - 1;
            int counter = counters.get(index);
            String previous = messages.get(index);
            if (Objects.equals(current, previous)) {
                counters.set(index, counter + 1);
            } else {
                counters.add(1);
                messages.add(current);
            }
        });

        IntStream.range(0, counters.size()).forEach(i -> {
            int counter = counters.get(i);
            result.add(messages.get(i) + (counter == 1 ? "" : String.format(" (x%d)", counter)));
        });

        return Observable.fromIterable(result);
    }
}
