package com.acme.edu.rx;

import com.acme.edu.rx.formatter.LogFormatter;
import com.acme.edu.rx.formatter.TrivialLogFormatter;
import com.acme.edu.rx.processor.ByteLogProcessor;
import com.acme.edu.rx.processor.IntegerLogProcessor;
import com.acme.edu.rx.processor.StringLogProcessor;
import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;

import java.io.PrintStream;
import java.util.Arrays;
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
        Observable.merge(Arrays.asList(
                setUpObjectProcessing().map(formatter::format),
                setUpBooleanProcessing().map(formatter::format),
                setUpByteProcessing().map(formatter::format),
                setUpIntegerProcessing().map(formatter::format),
                setUpCharProcessing().map(formatter::format),
                setUpStringProcessing().map(formatter::format),
                setUpIntArrayProcessing().map(formatter::format)
        )).subscribe(outputStream::println);
    }

    private Observable<Object> setUpObjectProcessing() {
        return getTypeStream(Object.class)
                .flatMap(Observable::fromIterable);
    }

    private Observable<Boolean> setUpBooleanProcessing() {
        return getTypeStream(Boolean.class)
                .flatMap(Observable::fromIterable);
    }

    private Observable<Byte> setUpByteProcessing() {
        return getTypeStream(Byte.class)
                .flatMap(ByteLogProcessor::process);
    }

    private Observable<Integer> setUpIntegerProcessing() {
        return getTypeStream(Integer.class)
                .flatMap(IntegerLogProcessor::process);
    }

    private Observable<Character> setUpCharProcessing() {
        return getTypeStream(Character.class)
                .flatMap(Observable::fromIterable);
    }

    private Observable<String> setUpStringProcessing() {
        return getTypeStream(String.class)
                .flatMap(StringLogProcessor::process);
    }

    private Observable<int[]> setUpIntArrayProcessing() {
        return getTypeStream(int[].class)
                .flatMap(Observable::fromIterable);
    }

    private <T> Observable<List<T>> getTypeStream(Class<T> clazz) {
        return stream
                .ofType(clazz)
                .filter(m -> m.getClass() == clazz)
                .buffer(stream.map(Object::getClass).distinctUntilChanged())
                .filter(l -> !l.isEmpty());
    }
}
