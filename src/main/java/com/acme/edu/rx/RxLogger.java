package com.acme.edu.rx;

import io.reactivex.subjects.PublishSubject;

public class RxLogger {
    private final PublishSubject<Integer> integerSubject;
    private final PublishSubject<String> stringSubject;

    public RxLogger() {
        integerSubject = PublishSubject.create();
        stringSubject = PublishSubject.create();

        setUpIntegerSubscriber();
        setUpStringSubcriber();
    }

    private void setUpIntegerSubscriber() {
        integerSubject
                .takeUntil(stringSubject)
                .reduce(
                        (x, y) -> x + y
                )
                .doOnComplete(this::setUpIntegerSubscriber)
                .subscribe(System.out::println);
    }

    private void setUpStringSubcriber() {
        stringSubject
                .takeUntil(integerSubject)
                .doOnComplete(this::setUpStringSubcriber)
                .subscribe(System.out::println);
    }

    public void log(Integer message) {
        integerSubject.onNext(message);
    }

    public void log(String message) {
        stringSubject.onNext(message);
    }

    public void flush() {
        integerSubject.onComplete();
        stringSubject.onComplete();
    }
}
