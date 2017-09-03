package com.acme.edu.rx;

import com.acme.edu.rx.formatter.LogFormatter;
import com.acme.edu.rx.formatter.TrivialLogFormatter;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

import java.io.PrintStream;
import java.util.List;

@SuppressWarnings({"WeakerAccess", "unchecked", "ConstantConditions"})
public class RxLogger {

    private PublishSubject<Object> stream;
    private LogFormatter formatter;
    private PrintStream outputStream;

    public RxLogger() {
        stream = PublishSubject.create();
        formatter = new TrivialLogFormatter();
        this.outputStream = System.out;
        setUpStreamProcessing();
    }

    public RxLogger(LogFormatter formatter, PrintStream outputStream) {
        stream = PublishSubject.create();
        this.formatter = formatter;
        this.outputStream = outputStream;
        setUpStreamProcessing();
    }

    public void log(Object message) {
        stream.onNext(message);
    }

    public void flush() {
        stream.onComplete();
        stream = PublishSubject.create();
        setUpStreamProcessing();
    }

    private void setUpStreamProcessing() {
        Observable.merge(
                setUpObjectProcessing().map(formatter::format),
                setUpIntegerProcessing().map(formatter::format),
                setUpStringProcessing().map(formatter::format),
                setUpArrayProcessor().map(formatter::format)
        ).subscribe(outputStream::println);
    }

    private Observable<Object> setUpObjectProcessing() {
        return getTypeStream(Object.class)
                .flatMap(Observable::fromArray);
    }

    private Observable<Integer> setUpIntegerProcessing() {
        return getTypeStream(Integer.class)
                .map(l -> l.stream().reduce((x, y) -> x + y).get());
    }

    private Observable<String> setUpStringProcessing() {
        return getTypeStream(String.class)
                .map(l -> l.stream().reduce((x, y) -> x + y).get());
    }

    private Observable<List> setUpArrayProcessor() {
        return getTypeStream(List.class)
                .flatMap(Observable::fromArray);
    }

    private <T> Observable<List<T>> getTypeStream(Class<T> clazz) {
        return stream
                .ofType(clazz)
                .filter(m -> m.getClass() == clazz)
                .buffer(stream.map(Object::getClass).distinctUntilChanged())
                .filter(l -> !l.isEmpty());
    }
}
