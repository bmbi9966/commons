package net.dongliu.commons;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class LazyTest {

    @Test
    public void of() {
        Lazy lazy = Lazy.of(Object::new);
        assertTrue(lazy.get() == lazy.get());
    }

    @Test(expected = RuntimeException.class)
    public void exception() {
        Lazy lazy = Lazy.of(() -> {
            throw new RuntimeException();
        });
        lazy.get();
    }
}