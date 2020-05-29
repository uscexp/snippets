package com.uscexp.lang.switchnice;

import org.junit.Test;

import static org.junit.Assert.*;

public class SwitchTest {

    @Test
    public void testSwitchMatchFirstCase() {
        String result = Switch.Switch("test")
                .caseOp("test", () -> {
                    return "OK";
                })
                .breakOp()

                .caseOp("test1", () -> {
                    return "NOT OK 1";
                })
                .breakOp()

                .defaultOp(() -> {
                    return "NOT OK 2";
                })
                .get();

        assertEquals("OK", result);
    }

    @Test
    public void testSwitchMatchSecondCase() {
        String value = "test";
        String toEvaluate = "test";
        SwitchSimple<String> aSwitchSimple = new SwitchSimple<>(value);

        String result = Switch.Switch("test")
                .caseOp("test1", () -> {
                    return "NOT OK 1";
                })
                .breakOp()

                .caseOp("test", () -> {
                    return "OK";
                })
                .breakOp()

                .defaultOp(() -> {
                    return "NOT OK 2";
                })
                .get();

        assertEquals("OK", result);
    }

    @Test
    public void testSwitchMatchDefault() {
        String value = "test";
        String toEvaluate = "test";
        SwitchSimple<String> aSwitchSimple = new SwitchSimple<>(value);

        String result = Switch.Switch("test")
                .caseOp("test1", () -> {
                    return "NOT OK 1";
                })
                .breakOp()

                .caseOp("test2", () -> {
                    return "NOT OK 2";
                })
                .breakOp()

                .defaultOp(() -> {
                    return "OK";
                })
                .get();

        assertEquals("OK", result);
    }

    @Test
    public void testSwitchMatchFirstAndSecondCase() {
        String value = "test";
        String toEvaluate = "test";
        SwitchSimple<String> aSwitchSimple = new SwitchSimple<>(value);

        Switch<String> aSwitch = Switch.Switch("test");
        String result = aSwitch
                .caseOp("test", () -> {
                    return "OK 1";
                })

                .caseOp("test2", () -> {
                    String st = aSwitch.get();
                    return st + ", OK 2";
                })
                .breakOp()

                .defaultOp(() -> {
                    return "NOT OK 2";
                })
                .get();

        assertEquals("OK 1, OK 2", result);
    }

    @Test(expected = IllegalStateException.class)
    public void testSwitchCaseAfterDefault() {
        String result = Switch.Switch("test")
                .caseOp("test", () -> {
                    return "OK";
                })
                .breakOp()

                .caseOp("test1", () -> {
                    return "NOT OK 1";
                })
                .breakOp()

                .defaultOp(() -> {
                    return "NOT OK 2";
                })
                .caseOp("test2", () -> {
                    return "NOT OK 3";
                })
                .get();

        assertEquals("OK", result);
    }

    @Test(expected = IllegalStateException.class)
    public void testSwitchBreakAfterDefault() {
        String result = Switch.Switch("test")
                .caseOp("test", () -> {
                    return "OK";
                })
                .breakOp()

                .caseOp("test1", () -> {
                    return "NOT OK 1";
                })
                .breakOp()

                .defaultOp(() -> {
                    return "NOT OK 2";
                })
                .breakOp()
                .get();

        assertEquals("OK", result);
    }
}