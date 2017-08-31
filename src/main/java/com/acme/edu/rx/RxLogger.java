package com.acme.edu.rx;

import io.reactivex.subjects.PublishSubject;

import java.util.HashMap;
import java.util.Map;

public class RxLogger {
    private final Map<Class, PublishSubject> subjects;

    public RxLogger() {
        subjects = new HashMap<>();
        subjects.put(Object.class, PublishSubject.create());
        subjects.put(Integer.class, PublishSubject.create());
        subjects.put(String.class, PublishSubject.create());

        setUpSubscribers();
    }

    public void log(Object message) {
        subjects.get(Object.class).onNext(message);
    }

    public void log(Integer message) {
        subjects.get(Integer.class).onNext(message);
    }

    public void log(String message) {
        subjects.get(String.class).onNext(message);
    }

    // todo redactor this method to avoid stackoverflow exception
    public void flush() {
        subjects.values().forEach(PublishSubject::onComplete);
        subjects.keySet().forEach(clazz -> subjects.put(clazz, PublishSubject.create()));
    }

    private void setUpSubscribers() {
        setUpOjectSubscriber();
        setUpIntegerSubscriber();
        setUpStringSubcriber();
    }

    private void setUpOjectSubscriber() {
        subjects.get(Object.class)
                .map(o -> "reference: " + o)
                .subscribe(System.out::println);
    }

    private void setUpIntegerSubscriber() {
        subjects.get(Integer.class)
                .takeUntil(subjects.get(String.class))
                .reduce(
                        (x, y) -> (int) x + (int) y
                )
                .doOnComplete(this::setUpIntegerSubscriber)
                .map(i -> "primitive: " + i)
                .subscribe(System.out::println);
    }

    private void setUpStringSubcriber() {
        subjects.get(String.class)
                .takeUntil(subjects.get(Integer.class))
                .doOnComplete(this::setUpStringSubcriber)
                .map(s -> "string: " + s)
                .subscribe(System.out::println);
    }

}
