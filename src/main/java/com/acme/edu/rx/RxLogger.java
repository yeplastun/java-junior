package com.acme.edu.rx;

import com.acme.edu.rx.exception.InvalidLogMessageException;
import com.acme.edu.rx.formatter.LogFormatter;
import com.acme.edu.rx.processor.ByteLogProcessor;
import com.acme.edu.rx.processor.IntegerLogProcessor;
import com.acme.edu.rx.processor.StringLogProcessor;
import com.acme.edu.rx.response.LogResponse;
import com.acme.edu.rx.saver.LogSaver;
import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.subjects.PublishSubject;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.List;

/**
 * Asynchronous logger with embedded business logic.
 */
@SuppressWarnings({"WeakerAccess", "unchecked", "ConstantConditions"})
public class RxLogger {

    private PublishSubject<Object> stream;
    private PublishSubject<LogResponse> responseStream;
    private LogFormatter formatter;
    private LogSaver saver;

    /**
     * Public constructor for RxLogger.
     *
     * @param formatter Implementation of <b>LogFormatter</b> that provides format function for log message.
     * @param saver     Implementation of <b>LogSaver</b> that provides api for saving log message anywhere you want.
     * @apiNote After creating new instance it's necessary to subscribe to responses
     * stream using {@link #getResponseStream()}.
     */
    public RxLogger(LogFormatter formatter, LogSaver saver) {
        stream = PublishSubject.create();
        responseStream = PublishSubject.create();
        this.formatter = formatter;
        this.saver = saver;
        setUpStreamProcessing();
    }

    /**
     * Provides separate stream of logging responses. All exceptions are instances {@link LogResponse}.
     * They notifies about log processing success or fail.
     *
     * @return {@link Observable} response stream.
     * To get exception use method {@link Observable#subscribe(Consumer)}.
     */
    public Observable<LogResponse> getResponseStream() {
        return responseStream;
    }

    /**
     * <b>Asynchronously</b> logs current message formatted by <b>LogFormatter</b>
     * implementation via <b>LogSaver</b> implementation.
     * <p>
     * To get response stream use {@link #getResponseStream()}.
     * To force output use {@link #flush()}.
     * </p>
     *
     * @param message the message to be logged.
     * @throws InvalidLogMessageException based on not-null check.
     */
    public void log(@NotNull Object message) throws InvalidLogMessageException {
        if (message == null) {
            throw new InvalidLogMessageException("Log message shouldn't be null", null);
        }
        stream.onNext(message);
    }

    /**
     * Interrupts current buffer business logic and forces logging current results.
     */
    public void flush() {
        stream.onComplete();
        stream = PublishSubject.create();
        setUpStreamProcessing();
    }

    private void setUpStreamProcessing() {
        Observable.merge(Arrays.asList(
            getTypeStream(Object.class).flatMap(Observable::fromIterable).map(formatter::format),
            getTypeStream(Boolean.class).flatMap(Observable::fromIterable).map(formatter::format),
            getTypeStream(Byte.class).flatMap(ByteLogProcessor::process).map(formatter::format),
            getTypeStream(Integer.class).flatMap(IntegerLogProcessor::process).map(formatter::format),
            getTypeStream(Character.class).flatMap(Observable::fromIterable).map(formatter::format),
            getTypeStream(String.class).flatMap(StringLogProcessor::process).map(formatter::format),
            getTypeStream(int[].class).flatMap(Observable::fromIterable).map(formatter::format)
        ))
            .doOnNext(saver::save)
            .subscribe(
                logMessage -> responseStream.onNext(new LogResponse(logMessage)),
                ex -> {
                    flush();
                    responseStream.onNext(new LogResponse(ex));
                });
    }

    private <T> Observable<List<T>> getTypeStream(Class<T> clazz) {
        return stream
            .ofType(clazz)
            .filter(m -> m.getClass() == clazz)
            .buffer(stream.map(Object::getClass).distinctUntilChanged())
            .filter(l -> !l.isEmpty());
    }
}
