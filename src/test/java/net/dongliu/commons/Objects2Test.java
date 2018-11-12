package net.dongliu.commons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class Objects2Test {

    @Test
    public void elvis() {
        assertEquals("1", Objects2.elvis("1", "2"));
        assertEquals("2", Objects2.elvis(null, "2"));
        assertEquals("1", Objects2.elvis("1", () -> "2"));
        assertEquals("2", Objects2.elvis(null, () -> "2"));
        assertThrows(NullPointerException.class, () -> Objects2.elvis(null, (Object) null));
        assertThrows(NullPointerException.class, () -> Objects2.elvis(null, () -> null));
    }

    @Test
    public void toStringHelper() {
        assertEquals("null", Objects2.toStringHelper(Object.class).toString(null));
        assertEquals("Integer{value=1}", Objects2.toStringHelper(Integer.class).toString(1));
        assertEquals("[1]", Objects2.toStringHelper(int[].class).toString(new int[]{1}));
        assertEquals("[1]", Objects2.toStringHelper(char[].class).toString(new char[]{'1'}));
        assertEquals("[1]", Objects2.toStringHelper(String[].class).toString(new String[]{"1"}));
        // Object class is special, it does has toString method...
        assertTrue(Objects2.toStringHelper(Object.class).toString(new Object()).contains("Object"));
        Object v = new Object() {
            private int i = 1;
        };
        assertEquals("Objects2Test$1{i=1}", Objects2.toStringHelper(v.getClass()).toString(v));

        // this unit test may be broken, fix it if necessary
        assertEquals("ToStrTest{i=1, str=abcd, l=10, chars=[c, d]}", Objects2.toStringHelper(ToStrTest.class)
                .toString(new ToStrTest()));

        Object v2 = new ToStrTest() {
            private int i = 5;
        };
        assertEquals("Objects2Test$2{i=5, str=abcd, l=10, chars=[c, d]}", Objects2.toStringHelper(v2.getClass()).toString(v2));
    }

    @Test
    void testToString() {
        assertEquals("null", Objects2.toString(null));
        assertEquals("Integer{value=1}", Objects2.toString(1));
        assertEquals("[1]", Objects2.toString(new int[]{1}));
        assertEquals("ToStrTest{i=1, str=abcd, l=10, chars=[c, d]}", Objects2.toString(new ToStrTest()));
    }

    private static class ToStrTest {
        private int i = 1;
        private String str = "abcd";
        private long l = 10L;
        private char[] chars = {'c', 'd'};
    }
}