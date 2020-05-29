package com.uscexp.lang.switchnice;

import java.util.function.Supplier;

public abstract class Switch<T> {

    protected Switch<T> root;

    protected Switch() {
    }

    public Switch(Switch<T> root) {
        this.root = root;
    }

    public void setResult(Object result) {
        if(root != null) {
            root.setResult(result);
        }
    }

    protected abstract T getValue();

    protected Switch<T> getRoot() {
        return root;
    }

    public <U> U get() {
        if(root != null) {
            return (U) root.get();
        } else {
            return null;
        }
    }

    public static <T> Switch<T> Switch(T value) {
        return new SwitchRoot<>(value);
    }

    public Switch<T> caseOp(T toEvaluate, Supplier<?> block) {
        Case aCase = new Case(getRoot(), this, toEvaluate);
        return aCase.process(block);
    }

    public Switch<T> breakOp() {
        Break aBreak = new Break(getRoot(), this);
        return aBreak.process(null);
    }

    public Switch<T> defaultOp(Supplier<?> block) {
        Default aDefault = new Default(getRoot(), this);
        return aDefault.process(block);
    }

    public abstract boolean executed();

    protected abstract Switch<T> process(Supplier<?> block);
}
