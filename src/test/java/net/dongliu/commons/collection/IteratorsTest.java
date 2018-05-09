package net.dongliu.commons.collection;

import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class IteratorsTest {

    @Test
    public void stream() {
        Iterator<Integer> iterator = Lists.of(1, 2).iterator();
        assertEquals(2, Iterators.stream(iterator).count());
    }

    @Test
    public void next() {
        Iterator<Integer> iterator = Lists.of(1, 2).iterator();

        assertEquals(Optional.of(1), Iterators.next(iterator));
        assertEquals(Optional.of(2), Iterators.next(iterator));
        assertEquals(Optional.empty(), Iterators.next(iterator));
    }

    @Test
    public void nextOrNull() {
        Iterator<Integer> iterator = Lists.of(1, 2).iterator();

        assertEquals(Integer.valueOf(1), Iterators.nextOrNull(iterator));
        assertEquals(Integer.valueOf(2), Iterators.nextOrNull(iterator));
        assertNull(Iterators.nextOrNull(iterator));
    }
}