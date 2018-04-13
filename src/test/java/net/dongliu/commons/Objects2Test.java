package net.dongliu.commons;

import org.junit.Test;

import static org.junit.Assert.*;

public class Objects2Test {

    @Test
    public void elvis() {
        assertEquals("1", Objects2.elvis("1", "2"));
        assertEquals("2", Objects2.elvis(null, "2"));
        assertEquals("1", Objects2.elvis("1", () -> "2"));
        assertEquals("2", Objects2.elvis(null, () -> "2"));
    }

    @Test(expected = NullPointerException.class)
    public void elvisException() {
        Objects2.elvis(null, (Object) null);
    }

    @Test(expected = NullPointerException.class)
    public void elvisException2() {
        Objects2.elvis(null, () -> null);
    }
}