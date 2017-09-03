package com.acme.edu.rx;

import io.reactivex.subjects.PublishSubject;

@SuppressWarnings({"WeakerAccess"})
public class RxLogger {

    private PublishSubject<Object> stream;

    public RxLogger() {
        stream = PublishSubject.create();
        setUpSubscribers();
    }

    public void log(Object message) {
        stream.onNext(message);
    }

    public void flush() {
        stream.onComplete();
        stream = PublishSubject.create();
        setUpSubscribers();
    }

    private void setUpSubscribers() {
        setUpObjectSubscriber();
        setUpIntegerSubscriber();
        setUpStringSubscriber();
    }

    private void setUpObjectSubscriber() {
        stream
                .filter(m -> m.getClass() == Object.class)
                .buffer(stream.map(Object::getClass).distinctUntilChanged())
                .filter(l -> !l.isEmpty())
                .subscribe(l -> l.forEach(m -> System.out.println("reference: " + m.toString())));
    }

    private void setUpIntegerSubscriber() {
        stream
                .ofType(Integer.class)
                .buffer(stream.map(Object::getClass).distinctUntilChanged())
                .filter(l -> !l.isEmpty())
                .subscribe(
                        l -> System.out.println(
                                "primitive: " + l.stream().reduce((x, y) -> x + y).get()
                        )
                );
    }

    private void setUpStringSubscriber() {
        stream
                .ofType(String.class)
                .buffer(stream.map(Object::getClass).distinctUntilChanged())
                .filter(l -> !l.isEmpty())
                .subscribe(
                        l -> System.out.println(
                                "string: " + l.stream().reduce((x, y) -> x + y).get()
                        )
                );
    }
}
