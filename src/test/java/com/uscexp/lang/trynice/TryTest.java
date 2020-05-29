package com.uscexp.lang.trynice;

import org.hamcrest.CoreMatchers;
import org.hamcrest.Matcher;
import org.hamcrest.core.IsInstanceOf;
import org.junit.Test;
import spock.util.matcher.HamcrestMatchers;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.junit.Assert.*;

public class TryTest {

    @Test(expected = FileNotFoundException.class)
    public void ofThrowable() throws Throwable {
        Try<FileInputStream> aTry = Try.ofThrowable(() -> {
            return new FileInputStream("test");
        });

        assertThat(aTry, instanceOf(Failure.class));
        aTry.get();
    }
}