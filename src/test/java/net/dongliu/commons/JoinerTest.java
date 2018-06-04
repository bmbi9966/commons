package net.dongliu.commons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JoinerTest {

    @Test
    void join() {
    }

    @Test
    void skipNulls() {
        assertEquals("1, 2", Joiner.of(", ").skipNulls(true).join(1, 2, null));
    }

    @Test
    void nullToEmpty() {
        assertEquals("1, 2, ", Joiner.of(", ").nullToEmpty(true).join(1, 2, null));
    }
}