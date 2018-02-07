package net.dongliu.commons;

import org.junit.Test;

import static org.junit.Assert.*;

public class LazyTest {

    @Test
    public void of() {
        Lazy lazy = Lazy.of(Object::new);
        assertEquals(lazy.get(), lazy.get());
    }

    @Test
    public void get() {
    }
}