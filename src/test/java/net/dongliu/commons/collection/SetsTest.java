package net.dongliu.commons.collection;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class SetsTest {

    @Test
    public void convertTo() {
        assertEquals(Sets.of(1, 2, 3), Sets.convertTo(Sets.of("1", "2", "3"), Integer::valueOf));
        assertEquals(Sets.of(1), Sets.convertTo(Sets.of("1"), Integer::valueOf));
        assertEquals(Sets.of(), Sets.convertTo(Sets.<String>of(), Integer::valueOf));
    }

    @Test
    public void filter() {
        assertEquals(Sets.of(1, 2, 3), Sets.filter(Sets.of(1, 2, 3, 4), i -> i < 4));
        assertEquals(Sets.of(), Sets.filter(Sets.of(1, 2, 3, 4), i -> i > 5));
    }

    @Test
    public void nullToEmpty() {
        assertEquals(Sets.of(), Sets.nullToEmpty(null));
        assertEquals(Sets.of(1, 2, 3), Sets.nullToEmpty(Sets.of(1, 2, 3)));
    }
}