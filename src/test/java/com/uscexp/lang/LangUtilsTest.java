package com.uscexp.lang;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.IOException;

import static org.junit.Assert.*;

public class LangUtilsTest {

    @Rule
    public ExpectedException exceptionRule = ExpectedException.none();

    class TestClass {
        private TestClass testClass;
        private int number;
        private String desc;

        public TestClass(TestClass testClass, int number, String desc) {
            this.testClass = testClass;
            this.number = number;
            this.desc = desc;
        }

        public TestClass getTestClass() {
            return testClass;
        }

        public void setTestClass(TestClass testClass) {
            this.testClass = testClass;
        }

        public int getNumber() {
            return number;
        }

        public void setNumber(int number) {
            this.number = number;
        }

        public String getDesc() throws Exception {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String receiveProblem() throws IOException {
            throw new IOException();
        }

        public int receiveNumberProblem() throws IOException {
            throw new IOException();
        }
    }

    @Test
    public void nullSaveGetChain() {
        TestClass testClass = new TestClass(null, 1, "desc");

        String result = LangUtils.nullSaveGetChainUnchecked(testClass::getDesc);

        assertEquals("desc", result);

        result = LangUtils.nullSaveGetChainUnchecked(() -> testClass.getTestClass().getDesc());

        assertNull(result);

        result = LangUtils.nullSaveGetChainUnchecked(() -> testClass.getTestClass().getDesc(), () -> testClass.getDesc());

        assertEquals("desc", result);

        result = LangUtils.nullSaveGetChainUnchecked(() -> testClass.getDesc(), () -> testClass.getTestClass().getDesc());

        assertEquals("desc", result);
    }

    @Test
    public void nullSaveGetChainWithException() throws Exception {
        exceptionRule.expect(IOException.class);

        TestClass testClass = new TestClass(null, 1, "desc");

        LangUtils.nullSaveGetChain(() -> testClass.receiveProblem());

        exceptionRule.expect(RuntimeException.class);

        LangUtils.nullSaveGetChainUnchecked(() -> testClass.receiveProblem());
    }

    @Test
    public void nullSaveGetChainForNumber() {
        TestClass testClass = new TestClass(null, 1, "desc");

        Integer result = LangUtils.nullSaveGetChainForNumberUnchecked(Integer.class, testClass::getNumber);

        assertEquals(1, result.intValue());

        result = LangUtils.nullSaveGetChainForNumberUnchecked(Integer.class, () -> testClass.getTestClass().getNumber());

        assertEquals(0, result.intValue());
    }

    @Test
    public void nullSaveGetChainForNumberWithException() throws Exception {
        exceptionRule.expect(IOException.class);

        TestClass testClass = new TestClass(null, 1, "desc");

        LangUtils.nullSaveGetChainForNumber(Integer.class, () -> testClass.receiveNumberProblem());

        exceptionRule.expect(RuntimeException.class);

        LangUtils.nullSaveGetChainForNumberUnchecked(Integer.class, () -> testClass.receiveNumberProblem());
    }

    @Test
    public void nullSaveMethodCall() throws Exception {
        TestClass testClass = new TestClass(null, 1, "desc");

        String result = LangUtils.nullSaveMethodCall(testClass, testClass::getDesc);

        assertEquals("desc", result);

        result = LangUtils.nullSaveMethodCall(testClass.getTestClass(), () -> testClass.getTestClass().getDesc());

        assertNull(result);
    }

    @Test
    public void nullSaveMethodCallWithException() throws Exception {
        exceptionRule.expect(IOException.class);
        TestClass testClass = new TestClass(null, 1, "desc");

        LangUtils.nullSaveMethodCall(testClass, testClass::receiveProblem);

        exceptionRule.expect(RuntimeException.class);

        LangUtils.nullSaveMethodCallUnchecked(testClass.getTestClass(), () -> testClass.receiveProblem());
    }

    @Test
    public void nullSaveMethodCallWithOnFailureValue() {
        TestClass testClass = new TestClass(null, 1, "desc");

        String result = LangUtils.nullSaveMethodCallUnchecked(testClass, testClass::getDesc);

        assertEquals("desc", result);

        result = LangUtils.nullSaveMethodCallUnchecked(testClass.getTestClass(), () -> testClass.getTestClass().getDesc(), "bla");

        assertEquals("bla", result);
    }

    @Test
    public void nullSaveMethodCallWithoutReturnValue() {
        TestClass testClass = new TestClass(null, 1, "desc");

        LangUtils.nullSaveMethodCallUnchecked(testClass, () -> testClass.setDesc("bla"));

        String result = LangUtils.nullSaveMethodCallUnchecked(testClass, () -> testClass.getDesc());

        assertEquals("bla", result);

        LangUtils.nullSaveMethodCallUnchecked(testClass.getTestClass(), () -> testClass.getTestClass().setDesc("desc"));
    }

    @Test
    public void nullSaveMethodCallWithoutReturnValueWithException() throws Exception {
        exceptionRule.expect(IOException.class);
        TestClass testClass = new TestClass(null, 1, "desc");

        LangUtils.nullSaveMethodCall(testClass, () -> testClass.receiveProblem());

        exceptionRule.expect(RuntimeException.class);

        LangUtils.nullSaveMethodCallUnchecked(testClass.getTestClass(), () -> testClass.receiveProblem());
    }

    @Test
    public void uncheckCall() {
        TestClass testClass = new TestClass(null, 1, "desc");

        String result = LangUtils.uncheckCall(() -> testClass.getDesc());

        assertEquals("desc", result);

        exceptionRule.expect(RuntimeException.class);

        LangUtils.uncheckCall(() -> testClass.receiveProblem());
    }
}