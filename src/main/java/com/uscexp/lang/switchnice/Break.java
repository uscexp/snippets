package com.uscexp.lang.switchnice;

import java.util.function.Supplier;

public class Break<T> extends Switch<T> {

    private Switch<T> previous;

    public Break(Switch<T> root, Switch<T> previous) {
        super(root);
        this.previous = previous;
    }

    @Override
    protected T getValue() {
        return null;
    }

    @Override
    public boolean executed() {
        return previous.executed();
    }

    @Override
    protected Switch<T> process(Supplier<?> block) {
        if(previous instanceof Default) {
            throw new IllegalStateException(String.format("You can not use %s after %s", "breakOp", "defaultOp"));
        }
        return this;
    }
}
