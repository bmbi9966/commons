package net.dongliu.commons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class StringRandomTest {

    @Test
    void asciiString() {
        var random = new StringRandom();
        var str = random.asciiString(10);
        assertEquals(10, str.length());
    }

    @Test
    void alphanumericString() {
        var random = new StringRandom();
        var str = random.alphanumericString(10);
        assertEquals(10, str.length());
    }
}