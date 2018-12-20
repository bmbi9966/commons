package net.dongliu.commons.collection;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class IterablesTest {

    @Test
    public void first() {
        assertEquals(Optional.of("1"), Iterables.first(Lists.of("1")));
        assertEquals(Optional.empty(), Iterables.first(Lists.of()));
    }

    @Test
    public void firstOrNull() {
        assertEquals("1", Iterables.firstOrNull(Lists.of("1")));
        assertNull(Iterables.firstOrNull(Lists.of()));
    }

    @Test
    public void stream() {
        assertEquals(1, Iterables.stream(Lists.of("1")).count());
    }

    @Test
    void find() {
        assertEquals(Optional.of("1"), Iterables.find(Lists.of("1"), s -> s.equals("1")));
        assertEquals(Optional.empty(), Iterables.find(Lists.of("1"), s -> s.equals("2")));
    }

    @Test
    void findOrNull() {
        assertEquals("1", Iterables.findOrNull(Lists.of("1"), s -> s.equals("1")));
        assertNull(Iterables.findOrNull(Lists.of("1"), s -> s.equals("2")));
    }

    @Test
    void forEach() {
        Iterables.forEach(Lists.of(), (v, last) -> {
            throw new RuntimeException();
        });
        Iterables.forEach(Lists.of("1"), (v, last) -> {
            assertEquals(v, "1");
            assertTrue(last);
        });
        Iterables.forEach(Lists.of("1", "2"), (v, last) -> {
            assertEquals(v.equals("2"), last);
        });
    }
}