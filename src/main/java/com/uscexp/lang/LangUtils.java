package com.uscexp.lang;

import java.util.concurrent.Callable;
import java.util.function.Supplier;

/**
 * Java language utils
 */
public final class LangUtils {

    private LangUtils() {
    }

    /**
     * Call the given get method chain and return the result, on {@link NullPointerException} the result will be null.
     * If the first statement result is null the second statement will be evaluated ...
     * The first successful result (!= null) will interrupt the statement evaluation iteration.
     *
     * @param statements one or more get method chain(s)
     * @param <T>        generic parameter type
     * @return evaluated statement or null
     * @throws Exception if there occurs an exception executing the statement it will be thrown, except a {@link NullPointerException}
     */
    @SafeVarargs
    public static <T> T nullSaveGetChain(Callable<T>... statements) throws Exception {
        T result = null;
        for (Callable<T> statement : statements) {
            try {
                result = statement.call();
                break;
            } catch (NullPointerException ex) {
                //continue;
            }
        }
        return result;
    }

    /**
     * Call the given get method chain and return the result, on {@link NullPointerException} the result will be null.
     * If the first statement result is null the second statement will be evaluated ...
     * The first successful result (!= null) will interrupt the statement evaluation iteration.
     *
     * @param statements one or more get method chain(s)
     * @param <T>        generic parameter type
     * @return evaluated statement or null
     * @throws RuntimeException if there occurs an exception executing the statement it will be thrown
     * wrapped in a {@link RuntimeException}, except a {@link NullPointerException}
     */
    @SafeVarargs
    public static <T> T nullSaveGetChainUnchecked(Callable<T>... statements) {
        T result = null;
        try {
            result = nullSaveGetChain(statements);
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }
        return result;
    }

    /**
     * Call the given get method chain and return the result, on {@link NullPointerException} the result will be 0.
     * If the first statement result is null the second statement will be evaluated ...
     * The first successful result (!= null) will interrupt the statement evaluation iteration.
     *
     * @param type       class of the generic parameter T
     * @param statements one or more get method chain(s)
     * @param <T>        generic parameter type
     * @return evaluated statement or 0
     * @throws Exception if there occurs an exception executing the statement it will be thrown, except a {@link NullPointerException}
     */
    @SafeVarargs
    public static <T extends Number> T nullSaveGetChainForNumber(Class<T> type, Callable<T>... statements) throws Exception {
        T result = nullSaveGetChain(statements);
        if (result == null) {
            result = type.cast(0);
        }
        return result;
    }

    /**
     * Call the given get method chain and return the result, on {@link NullPointerException} the result will be 0.
     * If the first statement result is null the second statement will be evaluated ...
     * The first successful result (!= null) will interrupt the statement evaluation iteration.
     *
     * @param type       class of the generic parameter T
     * @param statements one or more get method chain(s)
     * @param <T>        generic parameter type
     * @return evaluated statement or 0
     * @throws RuntimeException if there occurs an exception executing the statement it will be thrown
     * wrapped in a {@link RuntimeException}, except a {@link NullPointerException}
     */
    @SafeVarargs
    public static <T extends Number> T nullSaveGetChainForNumberUnchecked(Class<T> type, Callable<T>... statements) {
        T result = null;
        try {
            result = nullSaveGetChainForNumber(type, statements);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * Call the given method and return the result, if caller is null the result will be given onFailureValue.
     *
     * @param caller         object which calls the method
     * @param statement      method call
     * @param onFailureValue returned value if caller == null
     * @param <V>            generic parameter type of caller
     * @param <R>            generic parameter type of result
     * @return evaluated statement or null
     * @throws Exception if there occurs an exception executing the statement it will be thrown
     */
    public static <V, R> R nullSaveMethodCall(V caller, Callable<R> statement, R onFailureValue) throws Exception {
        if (caller != null) {
            return statement.call();
        } else {
            return onFailureValue;
        }
    }

    /**
     * Call the given method and return the result, if caller is null the result will be given onFailureValue.
     *
     * @param caller         object which calls the method
     * @param statement      method call
     * @param onFailureValue returned value if caller == null
     * @param <V>            generic parameter type of caller
     * @param <R>            generic parameter type of result
     * @return evaluated statement or null
     * @throws RuntimeException if there occurs an exception executing the statement it will be thrown
     * wrapped in a {@link RuntimeException}
     */
    public static <V, R> R nullSaveMethodCallUnchecked(V caller, Callable<R> statement, R onFailureValue) {
        R result = null;
        try {
            result = nullSaveMethodCall(caller, statement, onFailureValue);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }

    /**
     * Call the given method and return the result, if caller is null the result will be null.
     *
     * @param caller    object which calls the method
     * @param statement method call
     * @param <V>       generic parameter type of caller
     * @param <R>       generic parameter type of result
     * @throws Exception if there occurs an exception executing the statement it will be thrown
     */
    public static <V, R> R nullSaveMethodCall(V caller, Callable<R> statement) throws Exception {
        return nullSaveMethodCall(caller, statement, null);
    }

    /**
     * Call the given method and return the result, if caller is null the result will be null.
     *
     * @param caller    object which calls the method
     * @param statement method call
     * @param <V>       generic parameter type of caller
     * @param <R>       generic parameter type of result
     * @throws RuntimeException if there occurs an exception executing the statement it will be thrown
     * wrapped in a {@link RuntimeException}
     */
    public static <V, R> R nullSaveMethodCallUnchecked(V caller, Callable<R> statement) {
        return nullSaveMethodCallUnchecked(caller, statement, null);
    }

    /**
     * Call the given method, if caller is not null.
     *
     * @param caller    object which calls the method
     * @param statement method call
     * @param <V>       generic parameter type of caller
     * @throws Exception if there occurs an exception executing the statement it will be thrown
     */
    public static <V> void nullSaveMethodCall(V caller, ErrorHandlingRunnable statement) throws Exception {
        if (caller != null) {
            statement.run();
        }
    }

    /**
     * Call the given method, if caller is not null.
     *
     * @param caller    object which calls the method
     * @param statement method call
     * @param <V>       generic parameter type of caller
     * @throws RuntimeException if there occurs an exception executing the statement it will be thrown
     * wrapped in a {@link RuntimeException}
     */
    public static <V> void nullSaveMethodCallUnchecked(V caller, ErrorHandlingRunnable statement) {
        try {
            nullSaveMethodCall(caller, statement);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FunctionalInterface
    public interface ErrorHandlingRunnable {
        void run() throws Exception;
    }

    /**
     * wrap a possibly thrown exception to a {@link RuntimeException}
     *
     * @param statement method call
     * @param <V>       generic parameter type of caller
     * @return evaluated statement or wrap exception to {@link RuntimeException}
     */
    public static <V> V uncheckCall(Callable<V> statement) {
        try {
            return statement.call();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
