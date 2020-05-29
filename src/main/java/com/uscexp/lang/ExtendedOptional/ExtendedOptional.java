package com.uscexp.lang.ExtendedOptional;

import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public final class ExtendedOptional<T> {

    private Class<T> type;
    private Optional<T> optional;

    private ExtendedOptional(Class<T> type, Optional<T> optional) {
        this.type = type;
        this.optional = optional;
    }

    public static <T> ExtendedOptional ofNullable(Class<T> type, T object) {
        return new ExtendedOptional<>(type, Optional.ofNullable(object));
    }

    public static <T> ExtendedOptional empty(Class<T> type) {
        return ofNullable(type, null);
    }

    public static <T> ExtendedOptional of(T object) {
        return new ExtendedOptional<>((Class<T>) object.getClass(), Optional.of(object));
    }

    public T get() {
        return optional.get();
    }

    public boolean isPresent() {
        return optional.isPresent();
    }

    public void ifPresent(Consumer<? super T> consumer) {
        optional.ifPresent(consumer);
    }

    public Optional<T> filter(Predicate<? super T> predicate) {
        return optional.filter(predicate);
    }

    public <U> Optional<U> map(Function<? super T, ? extends U> function) {
        return optional.map(function);
    }

    public <U> Optional<U> flatMap(Function<? super T, Optional<U>> function) {
        return optional.flatMap(function);
    }

    public T orElse(T t) {
        return optional.orElse(t);
    }

    public T orElseGet(Supplier<? extends T> supplier) {
        return optional.orElseGet(supplier);
    }

    public <X extends Throwable> T orElseThrow(Supplier<? extends X> supplier) throws X {
        return optional.orElseThrow(supplier);
    }

    public static <T> ExtendedOptional ofOptional(Class<T> type, Optional<T> optional) {
        return new ExtendedOptional<>(type, optional);
    }

    public <R> Optional<R> methodCallIfPresent(Callable<R> call) throws Exception {
        R result;
        if (isPresent()) {
            result = call.call();
        } else {
            result = null;
        }
        return Optional.ofNullable(result);
    }

    public <R> Optional<R> methodCallIfPresentUnchecked(Callable<R> call) {
        try {
            return methodCallIfPresent(call);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public <R> ExtendedOptional<R> methodCallIfPresent(Class<R> type, Callable<R> call) throws Exception {
        R result;
        if (isPresent()) {
            result = call.call();
        } else {
            result = null;
        }
        return ExtendedOptional.ofNullable(type, result);
    }

    public <R> ExtendedOptional<R> methodCallIfPresentUnchecked(Class<R> type, Callable<R> call) {
        try {
            return methodCallIfPresent(type, call);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
