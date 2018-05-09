package net.dongliu.commons;

import net.dongliu.commons.io.Bits;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BitsTest {

    @Test
    public void test() {
        assertTrue(Bits.test(1, 0));
        assertFalse(Bits.test(1, 1));
        assertTrue(Bits.test(-1, 10));
    }

    @Test
    public void set() {
        assertEquals(9, Bits.set(1, 3));
        assertEquals(9, Bits.set(9, 3));
        assertEquals(-1, Bits.set(Integer.MAX_VALUE, 31));
    }

    @Test
    public void clear() {
        assertEquals(0, Bits.clear(1, 0));
        assertEquals(1, Bits.clear(9, 3));
        assertEquals(Integer.MAX_VALUE, Bits.clear(-1, 31));
    }

    @Test
    public void take() {
        assertEquals(0xef, Bits.take(0xefef, 0, 8));
        assertEquals(0xef00, Bits.take(0xefef, 8, 16));
        assertEquals(0xff00, Bits.take(-1, 8, 16));
        assertEquals(0xfe00, Bits.take(0xfefefefe, 8, 16));
    }

    @Test
    public void takeDown() {
        assertEquals(0xef, Bits.takeDown(0xefef, 0, 8));
        assertEquals(0xef, Bits.takeDown(0xefef, 8, 16));
        assertEquals(0xff, Bits.takeDown(-1, 8, 16));
        assertEquals(0xfe, Bits.takeDown(0xfefefefe, 8, 16));
    }

    @Test
    public void flip() {
        assertEquals(9, Bits.flip(1, 3));
        assertEquals(1, Bits.flip(9, 3));
        assertEquals(6, Bits.flip(9, 0, 4));
    }
}