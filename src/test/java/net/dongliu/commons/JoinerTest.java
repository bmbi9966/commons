package net.dongliu.commons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JoinerTest {

    @Test
    void join() {
    }

    @Test
    void skipNulls() {
        assertEquals("1, 2", Joiner.of(", ").skipNulls().join(1, 2, null));
    }

    @Test
    void nullToEmpty() {
        assertEquals("1, 2, ", Joiner.of(", ").nullToEmpty().join(1, 2, null));
    }

    @Test
    void skipEmpty() {
        assertEquals("1, 2, null", Joiner.of(", ").skipEmpty().join(1, 2, "", null));
        assertEquals("1, 2", Joiner.of(", ").nullToEmpty().skipEmpty().join(1, 2, "", null));
    }
}