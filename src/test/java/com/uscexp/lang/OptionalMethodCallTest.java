package com.uscexp.lang;

import org.junit.Test;

import static org.junit.Assert.*;

public class OptionalMethodCallTest {

    static class TestClass {

        public TestClass() {
        }

        public String doSomeThing(String input) {
            return input;
        }
    }

    @Test
    public void testOptionalMethodCall() {
//        TestClass testClass = new TestClass();
//        OptionalMethodCall<TestClass> optionalMethodCall = OptionalMethodCall.ofNullable(TestClass.class, testClass);
//
//        String result = optionalMethodCall.methodCall().doSomeThing("TEST");
//
//        assertNotNull(optionalMethodCall);
//        assertEquals("TEST", result);
    }
}