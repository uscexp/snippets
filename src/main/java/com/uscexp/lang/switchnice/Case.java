package com.uscexp.lang.switchnice;

import java.util.Objects;
import java.util.function.Supplier;

public class Case<T, U> extends Switch<T> {

    private Switch<T> previous;
    private T toEvaluate;
    private boolean executed = false;

    public Case(Switch<T> root, Switch<T> previous, T toEvaluate) {
        super(root);
        this.previous = previous;
        this.toEvaluate = toEvaluate;
        this.executed = previous.executed();
    }

    @Override
    protected T getValue() {
        return toEvaluate;
    }

    @Override
    protected Switch<T> process(Supplier<?> block) {
        Objects.requireNonNull(block);
        if(previous instanceof Default) {
            throw new IllegalStateException(String.format("You can not use %s after %s", "caseOp", "defaultOp"));
        }
        if(previous instanceof Break || previous instanceof SwitchRoot) {
            if(getRoot().getValue().equals(toEvaluate)) {
                setResult(block.get());
                executed = true;
            }
        } else {
            setResult(block.get());
            executed = true;
        }
        return this;
    }

    @Override
    public boolean executed() {
        return executed;
    }
}
