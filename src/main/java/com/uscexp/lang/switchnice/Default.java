package com.uscexp.lang.switchnice;

import java.util.Objects;
import java.util.function.Supplier;

public class Default<T, U> extends Switch<T> {
    private Switch<T> previous;
    private boolean executed = false;

    public Default(Switch<T> root, Switch<T> previous) {
        super(root);
        this.previous = previous;
    }

    @Override
    protected T getValue() {
        return null;
    }

    @Override
    public boolean executed() {
        return executed;
    }

    @Override
    protected Switch<T> process(Supplier<?> block) {
        Objects.requireNonNull(block);
        if(!previous.executed()) {
            setResult(block.get());
            executed = true;
        }
        return this;
    }
}
