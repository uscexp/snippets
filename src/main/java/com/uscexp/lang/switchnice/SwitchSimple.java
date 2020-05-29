package com.uscexp.lang.switchnice;

import java.util.function.Supplier;

public class SwitchSimple<T> {

    protected T value;
    protected Object caseResult;
    protected boolean cont = true;
    protected boolean eval = false;

    public SwitchSimple(T value) {
        this.value = value;
    }

    public <U> U get() {
        return (U) caseResult;
    }

    public <U> SwitchSimple<T> Case(T toEvaluate, Supplier<? extends U> caseBlock) {
        if ((eval && cont) || (cont && value.equals(toEvaluate))) {
            eval = true;
            this.caseResult = caseBlock.get();
        }
        return this;
    }

    public SwitchSimple<T> Break() {
        if(this.caseResult != null) {
            cont = false;
        }
        return this;
    }

    public <U> SwitchSimple<T> Default(Supplier<? extends U> defaultBlock) {
        if(eval && cont) {
            cont = false;
            this.caseResult = defaultBlock.get();
        }
        return this;
    }
}
