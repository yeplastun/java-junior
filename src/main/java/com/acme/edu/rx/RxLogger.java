package com.acme.edu.rx;

import com.acme.edu.rx.exception.InvalidLogMessageException;
import com.acme.edu.rx.exception.LogMessageException;
import com.acme.edu.rx.formatter.LogFormatter;
import com.acme.edu.rx.processor.ByteLogProcessor;
import com.acme.edu.rx.processor.IntegerLogProcessor;
import com.acme.edu.rx.processor.StringLogProcessor;
import com.acme.edu.rx.saver.LogSaver;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

@SuppressWarnings({"WeakerAccess", "unchecked", "ConstantConditions"})
public class RxLogger {

    @SuppressWarnings("UnnecessaryReturnStatement")
    private static final Consumer<Object> NOOP = whatever -> { return; };

    private PublishSubject<Object> stream;
    private PublishSubject<LogMessageException> exceptionStream;
    private LogFormatter formatter;
    private LogSaver saver;

    public RxLogger(LogFormatter formatter, LogSaver saver) {
        stream = PublishSubject.create();
        exceptionStream = PublishSubject.create();
        this.formatter = formatter;
        this.saver = saver;
        setUpStreamProcessing();
    }

    public PublishSubject<LogMessageException> getExceptionStream() {
        return exceptionStream;
    }

    public void log(@NotNull Object message) throws InvalidLogMessageException {
        if (message == null) {
            throw new InvalidLogMessageException("Log message shouldn't be null", null);
        }
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
        ))
            .doOnNext(saver::save)
            .subscribe(NOOP, ex -> {
                flush();
                exceptionStream.onNext((LogMessageException) ex);
            });
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
