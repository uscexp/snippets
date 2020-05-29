package com.uscexp.lang.ExtendedOptional;

import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.*;

public class ExtendedOptionalTest {

    @Test
    public void testOptionalMethodCall() throws Exception {
        String testClass = "test";
        ExtendedOptional<String> extendedOptional = ExtendedOptional.ofNullable(String.class, testClass);

        Optional<String> result = extendedOptional.methodCallIfPresent(() -> extendedOptional.get().toUpperCase());

        assertNotNull(extendedOptional);
        assertEquals("TEST", result.get());
    }

    @Test
    public void testOptionalMethodCallWithType() throws Exception {
        String testClass = "test";
        ExtendedOptional<String> extendedOptional = ExtendedOptional.ofNullable(String.class, testClass);

        ExtendedOptional<String> result = extendedOptional.methodCallIfPresent(String.class, () -> extendedOptional.get().toUpperCase());

        assertNotNull(extendedOptional);
        assertEquals("TEST", result.get());
    }
}