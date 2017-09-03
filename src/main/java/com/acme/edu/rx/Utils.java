package com.acme.edu.rx;

import io.reactivex.Observable;

import java.util.Objects;

@SuppressWarnings("WeakerAccess")
class Utils {
    static boolean isNot(Class message, Class condition) {
        return !Objects.equals(message, condition);
    }

    static boolean is(Class message, Class condition) {
        return Objects.equals(message, condition);
    }

    static <T> Observable<T> getStream(Class<T> clazz, Observable<Object> stream) {
        return stream
                .filter(m -> is(m.getClass(), clazz))
                .ofType(clazz);
    }
}
