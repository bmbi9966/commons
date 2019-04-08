package net.dongliu.commons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RandomsTest {

    @Test
    void nextInt() {
        assertEquals(0, Randoms.nextInt(0, 1));
        int random = Randoms.nextInt(0, 2);
        assertTrue(random >= 0 && random < 2);
        assertThrows(IllegalArgumentException.class, () -> Randoms.nextInt(0, 0));
    }

    @Test
    void bytes() {
        byte[] bytes = Randoms.bytes(10);
        assertEquals(10, bytes.length);
    }
}