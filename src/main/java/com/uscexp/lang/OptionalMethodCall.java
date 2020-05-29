package com.uscexp.lang;

//import net.sf.cglib.proxy.Enhancer;
//import net.sf.cglib.proxy.MethodInterceptor;
//import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public final class OptionalMethodCall<T> { // extends Enhancer implements MethodInterceptor {

    private Class<T> type;
    private Optional<T> optional;

    private OptionalMethodCall(Class<T> type, Optional<T> optional) {
        this.type = type;
        this.optional = optional;
    }

    public static <T> OptionalMethodCall ofNullable(Class<T> type, T object) {
        return new OptionalMethodCall<>(type, Optional.ofNullable(object));
    }

    public static <T> OptionalMethodCall empty(Class<T> type) {
        return ofNullable(type, null);
    }

    public static <T> OptionalMethodCall of(T object) {
        return new OptionalMethodCall<>((Class<T>) object.getClass(), Optional.of(object));
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

    public static <T> OptionalMethodCall ofOptional(Class<T> type, Optional<T> optional) {
        return new OptionalMethodCall<>(type, optional);
    }

//    public T methodCall() {
//        this.setSuperclass(type);
//        this.setCallback(this);
//        return (T) this.create();
//    }

//    @Override
//    public Object intercept(Object obj, Method method, Object[] args, MethodProxy proxy) throws Throwable {
//        Object result = null;
//        if (optional.isPresent()) {
//            result = method.invoke(optional.get(), args);
//        }
//        return result;
//    }
}
