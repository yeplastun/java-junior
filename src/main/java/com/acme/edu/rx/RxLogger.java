package com.acme.edu.rx;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

import java.util.HashMap;
import java.util.Map;

@SuppressWarnings({"unchecked", "WeakerAccess"})
public class RxLogger {

    private Map<Class<?>, PublishSubject> subjects;

    public RxLogger() {
        createSubjects();
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

    public void flush() {
        completeSubjects();
        createSubjects();
        setUpSubscribers();
    }

    private void createSubjects() {
        subjects = new HashMap<>();
        subjects.put(Object.class, PublishSubject.create());
        subjects.put(Integer.class, PublishSubject.<Integer>create());
        subjects.put(String.class, PublishSubject.<String>create());
    }

    private void completeSubjects() {
        subjects.values().forEach(PublishSubject::onComplete);
    }

    private void setUpSubscribers() {
        setUpObjectSubscriber();
        setUpIntegerSubscriber();
        setUpStringSubscriber();
    }

    private void setUpObjectSubscriber() {
        if (subjects.get(Object.class).hasComplete()) return;
        subjects.get(Object.class)
                .map(o -> "reference: " + o)
                .subscribe(System.out::println);
    }

    private void setUpIntegerSubscriber() {
        if (subjects.get(Integer.class).hasComplete()) return;
        subjects.get(Integer.class)
                .takeUntil(Observable.merge(
                        subjects.get(Object.class),
                        subjects.get(String.class)
                ))
                .reduce(
                        (x, y) -> (int) x + (int) y
                )
                .doOnComplete(this::setUpIntegerSubscriber)
                .map(i -> "primitive: " + i)
                .subscribe(System.out::println);
    }

    private void setUpStringSubscriber() {
        if (subjects.get(String.class).hasComplete()) return;
        subjects.get(String.class)
                .takeUntil(Observable.merge(
                        subjects.get(Object.class),
                        subjects.get(Integer.class),
                        subjects.get(String.class)
                                .distinctUntilChanged()
                                .skip(1)
                ))
                .reduce(
                        (x, y) -> (String) x + y
                )
                .doOnComplete(this::setUpStringSubscriber)
                .map(s -> "string: " + s)
                .subscribe(System.out::println);
    }

}
