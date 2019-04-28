package net.dongliu.commons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class JoinerTest {

    @Test
    void join() {
    }

    @Test
    void skipNulls() {
        assertEquals("1, 2", Joiner.builder().delimiter(", ").skipNulls().build().join(1, 2, null));
    }

    @Test
    void nullToEmpty() {
        assertEquals("1, 2, ", Joiner.builder().delimiter(", ").nullToEmpty().build().join(1, 2, null));
    }

    @Test
    void skipEmpty() {
        assertEquals("1, 2, null", Joiner.builder().delimiter(", ").skipEmpty().build().join(1, 2, "", null));
        assertEquals("1, 2", Joiner.builder().delimiter(", ").nullToEmpty().skipEmpty().build().join(1, 2, "", null));
    }
}