package net.dongliu.commons;

import org.junit.Test;

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
    }

    @Test(expected = NullPointerException.class)
    public void checkArrayAndRange0() {
        byte[] bytes = null;
        Preconditions.checkArrayAndRange(bytes, 0, 10);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void checkArrayAndRange1() {
        byte[] bytes = new byte[256];
        Preconditions.checkArrayAndRange(bytes, 0, 257);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void checkArrayAndRange10() {
        byte[] bytes = new byte[256];
        Preconditions.checkArrayAndRange(bytes, 1, 256);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void checkArrayAndRange2() {
        byte[] bytes = new byte[256];
        Preconditions.checkArrayAndRange(bytes, -1, 2);
    }
}