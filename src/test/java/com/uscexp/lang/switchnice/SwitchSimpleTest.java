package com.uscexp.lang.switchnice;

import org.junit.Test;

import static org.junit.Assert.*;

public class SwitchSimpleTest {

    @Test
    public void testSwitchMatchFirstCase() {
        String value = "test";
        String toEvaluate = "test";
        SwitchSimple<String> aSwitchSimple = new SwitchSimple<>(value);

        String result = aSwitchSimple

                .Case(toEvaluate, () -> {
                    return "OK";
                })
                .Break()

                .Case("wrong test", () -> {
                    return "NOT OK 1";
                })
                .Break()

                .Default(() -> {
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

        String result = aSwitchSimple

                .Case("wrong test", () -> {
                    return "NOT OK 1";
                })
                .Break()

                .Case(toEvaluate, () -> {
                    return "OK";
                })
                .Break()

                .Default(() -> {
                    return "NOT OK 2";
                })

                .get();

        assertEquals("OK", result);
    }

    @Test
    public void testSwitchMatchDefault() {
        String value = "test";
        String toEvaluate = "not test";
        SwitchSimple<String> aSwitchSimple = new SwitchSimple<>(value);

        String result = aSwitchSimple

                .Case("wrong test", () -> {
                    return "NOT OK 1";
                })
                .Break()

                .Case(toEvaluate, () -> {
                    return "NOT OK 2";
                })
                .Break()

                .Default(() -> {
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

        String result = aSwitchSimple

                .Case(toEvaluate, () -> {
                    return "OK 1";
                })

                .Case("wrong test", () -> {
                    String st = aSwitchSimple.get();
                    return st + ", OK 2";
                })
                .Break()

                .Default(() -> {
                    return "NOT OK 1";
                })

                .get();

        assertEquals("OK 1, OK 2", result);
    }
}