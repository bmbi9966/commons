package net.dongliu.commons.collection;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class IterablesTest {

    @Test
    public void first() {
        assertEquals(Optional.of("1"), Iterables.first(List.of("1")));
        assertEquals(Optional.empty(), Iterables.first(List.of()));
    }

    @Test
    public void firstOrNull() {
        assertEquals("1", Iterables.firstOrNull(List.of("1")));
        assertNull(Iterables.firstOrNull(List.of()));
    }

    @Test
    public void stream() {
        assertEquals(1, Iterables.stream(List.of("1")).count());
    }

    @Test
    void find() {
        assertEquals(Optional.of("1"), Iterables.find(List.of("1"), s -> s.equals("1")));
        assertEquals(Optional.empty(), Iterables.find(List.of("1"), s -> s.equals("2")));
    }

    @Test
    void findOrNull() {
        assertEquals("1", Iterables.findOrNull(List.of("1"), s -> s.equals("1")));
        assertNull(Iterables.findOrNull(List.of("1"), s -> s.equals("2")));
    }

    @Test
    void forEach() {
        Iterables.forEach(List.of(), (v, last) -> {
            throw new RuntimeException();
        });
        Iterables.forEach(List.of("1"), (v, last) -> {
            assertEquals(v, "1");
            assertTrue(last);
        });
        Iterables.forEach(List.of("1", "2"), (v, last) -> {
            assertEquals(v.equals("2"), last);
        });
    }
}