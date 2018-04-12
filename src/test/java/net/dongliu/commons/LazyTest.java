package net.dongliu.commons;

import org.junit.Test;

import static org.junit.Assert.*;

public class LazyTest {

    @Test
    public void of() {
        Lazy lazy = Lazy.of(Object::new);
        assertSame(lazy.get(), lazy.get());
    }

    @Test(expected = RuntimeException.class)
    public void exception() {
        Lazy lazy = Lazy.of(() -> {
            throw new RuntimeException();
        });
        lazy.get();
    }

    @Test
    public void map() {
        Lazy<Integer> v = Lazy.of(() -> 1);
        Lazy<String> v2 = v.map(i -> i + 1).map(String::valueOf);
        assertEquals("2", v2.get());
    }
}