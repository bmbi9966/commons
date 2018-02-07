package net.dongliu.commons.collection;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ListsTest {

    @Test
    public void convertTo() {
        assertEquals(Lists.of(1, 2, 3), Lists.convertTo(Lists.of("1", "2", "3"), Integer::valueOf));
        assertEquals(Lists.of(1), Lists.convertTo(Lists.of("1"), Integer::valueOf));
        assertEquals(Lists.of(), Lists.convertTo(Lists.<String>of(), Integer::valueOf));
    }

    @Test
    public void filter() {
        assertEquals(Lists.of(1, 2, 3), Lists.filter(Lists.of(1, 2, 3, 4), i -> i < 4));
        assertEquals(Lists.of(), Lists.filter(Lists.of(1, 2, 3, 4), i -> i > 5));
    }

    @Test
    public void split() {
        assertEquals(Lists.of(Lists.of(1), Lists.of(2), Lists.of(3), Lists.of(4)), Lists.split(Lists.of(1, 2, 3, 4), 1));
        assertEquals(Lists.of(Lists.of(1, 2), Lists.of(3, 4)), Lists.split(Lists.of(1, 2, 3, 4), 2));
        assertEquals(Lists.of(Lists.of(1, 2, 3), Lists.of(4)), Lists.split(Lists.of(1, 2, 3, 4), 3));
        assertEquals(Lists.of(Lists.of(1, 2, 3, 4)), Lists.split(Lists.of(1, 2, 3, 4), 4));
        assertEquals(Lists.of(Lists.of(1, 2, 3, 4)), Lists.split(Lists.of(1, 2, 3, 4), 5));
        assertEquals(Lists.of(Lists.of()), Lists.split(Lists.<Integer>of(), 5));
    }

    @Test
    public void partition() {
        assertEquals(Pair.of(Lists.of(1), Lists.of(2, 3, 4)), Lists.partition(Lists.of(1, 2, 3, 4), i -> i < 2));
        assertEquals(Pair.of(Lists.of(), Lists.of(1, 2, 3, 4)), Lists.partition(Lists.of(1, 2, 3, 4), i -> i < 1));
        assertEquals(Pair.of(Lists.of(), Lists.of()), Lists.partition(Lists.<Integer>of(), i -> i < 1));
    }
}