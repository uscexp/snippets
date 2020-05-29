package com.uscexp.lang.switchnice;

import java.util.function.Supplier;

public class SwitchRoot<T> extends Switch<T> {

    private final T value;
    private Object result;

    public SwitchRoot(T value) {
        super();
        this.root = this;
        this.value = value;
    }

    @Override
    public void setResult(Object result) {
        this.result = result;
    }

    @Override
    public <U> U get() {
        return (U) result;
    }

    @Override
    protected T getValue() {
        return value;
    }

    @Override
    public boolean executed() {
        return false;
    }

    @Override
    protected Switch<T> process(Supplier<?> block) {
        return this;
    }
}
