package net.dongliu.commons.collection;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class IterablesTest {

    @Test
    public void first() {
        assertEquals(Optional.of("1"), Iterables.first(Sets.of("1")));
        assertEquals(Optional.empty(), Iterables.first(Sets.of()));
    }

    @Test
    public void firstOrNull() {
        assertEquals("1", Iterables.firstOrNull(Sets.of("1")));
        assertNull(Iterables.firstOrNull(Sets.of()));
    }

    @Test
    public void stream() {
        assertEquals(1, Iterables.stream(Sets.of("1")).count());
    }
}