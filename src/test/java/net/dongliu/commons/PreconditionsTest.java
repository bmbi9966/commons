package net.dongliu.commons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class PreconditionsTest {

    @Test
    public void checkSubRange() {
    }

    @Test
    public void checkArrayAndRange() {
        byte[] bytes = new byte[256];
        Preconditions.checkArrayAndRange(bytes, 0, 256);
        Preconditions.checkArrayAndRange(bytes, 1, 254);
        Preconditions.checkArrayAndRange(bytes, 0, 0);

        byte[] bytes2 = null;
        assertThrows(NullPointerException.class, () -> Preconditions.checkArrayAndRange(bytes2, 0, 10));
    }

    @Test
    public void checkArrayAndRange1() {
        byte[] bytes = new byte[256];
        assertThrows(IndexOutOfBoundsException.class, () -> Preconditions.checkArrayAndRange(bytes, 0, 257));
        byte[] bytes2 = new byte[256];
        assertThrows(IndexOutOfBoundsException.class, () -> Preconditions.checkArrayAndRange(bytes2, 1, 256));
        byte[] bytes3 = new byte[256];
        assertThrows(IndexOutOfBoundsException.class, () -> Preconditions.checkArrayAndRange(bytes3, -1, 2));
    }
}