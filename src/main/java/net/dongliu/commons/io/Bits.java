package net.dongliu.commons.io;

/**
 * Util for bit operation.
 * The methods of this class do not do any range check, caller should take care this.
 */
public class Bits {

    /**
     * If the bit in value is set.
     *
     * @param v   the value
     * @param idx bit index, starts with 0, from low to high
     * @return if bit is set
     */
    public static boolean test(int v, int idx) {
        return (v & (1 << idx)) != 0;
    }

    /**
     * Set the bit in value
     *
     * @param v   the value
     * @param idx bit index, starts with 0, from low to high
     * @return new value with the bit set
     */
    public static int set(int v, int idx) {
        return v | (1 << idx);
    }

    /**
     * Clear the bit in value
     *
     * @param v   the value
     * @param idx bit index, starts with 0, from low to high
     * @return new value with the bit cleared
     */
    public static int clear(int v, int idx) {
        return v & (~(1 << idx));
    }

    /**
     * Set the bit in value to the complement of current.
     *
     * @param v   the value
     * @param idx bit index, starts with 0, from low to high
     * @return new value with the bit flipped
     */
    public static int flip(int v, int idx) {
        return v ^ (1 << idx);
    }

    private static class MarkHolder {
        private static final int[] marks = {
                0, 1, 3, 7, 0xf,
                0x1f, 0x3f, 0x7f, 0xff,
                0x1ff, 0x3ff, 0x7ff, 0xfff,
                0x1fff, 0x3fff, 0x7fff, 0xffff,
                0x1ffff, 0x3ffff, 0x7ffff, 0xfffff,
                0x1fffff, 0x3fffff, 0x7fffff, 0xffffff,
                0x1ffffff, 0x3ffffff, 0x7ffffff, 0xfffffff,
                0x1fffffff, 0x3fffffff, 0x7fffffff, 0xffffffff
        };
    }

    /**
     * Set bits of value, in range [from, to). The indexes are starts with 0, from low to high.
     *
     * @param v    the original value
     * @param from the from bit index, include
     * @param to   the to bit index, exclude
     * @return the value with bits set
     */
    public static int set(int v, int from, int to) {
        return v | (MarkHolder.marks[to - from] << from);
    }

    /**
     * Clear bits of value, in range [from, to).The indexes are starts with 0, from low to high.
     *
     * @param v    the original value
     * @param from the from bit index, include
     * @param to   the to bit index, exclude
     * @return the value with bits cleared
     */
    public static int clear(int v, int from, int to) {
        return v & (~(MarkHolder.marks[to - from] << from));
    }

    /**
     * Set bits of value, in range [from, to), to the complement of current values.
     * The indexes are starts with 0, from low to high.
     *
     * @param v    the original value
     * @param from the from bit index, include
     * @param to   the to bit index, exclude
     * @return the value with bits flipped
     */
    public static int flip(int v, int from, int to) {
        return v ^ (MarkHolder.marks[to - from] << from);
    }

    /**
     * Take bits of value, in range [from, to), to be a new value. The other bits of new value is zero.
     * The indexes are starts with 0, from low to high.
     *
     * @param v    the original value
     * @param from the from bit index, include
     * @param to   the to bit index, exclude
     * @return the new value
     */
    public static int take(int v, int from, int to) {
        return v & (MarkHolder.marks[to - from] << from);
    }

    /**
     * Take bits of value, in range [from, to), and then shift to lowest bits, to be a new value.
     * The other bits of new value is zero.
     * The indexes are starts with 0, from low to high.
     *
     * @param v    the original value
     * @param from the from bit index, include
     * @param to   the to bit index, exclude
     * @return the new value
     */
    public static int takeDown(int v, int from, int to) {
        return (v >>> from) & MarkHolder.marks[to - from];
    }

    /**
     * If the bit in value is set.
     *
     * @param v   the value
     * @param idx bit index, starts with 0, from low to high
     * @return if bit is set
     */
    public static boolean test(long v, int idx) {
        return (v & (1L << idx)) != 0;
    }

    /**
     * Set the bit in value
     *
     * @param v   the value
     * @param idx bit index, starts with 0, from low to high
     * @return new value with the bit set
     */
    public static long set(long v, int idx) {
        return v | (1L << idx);
    }

    /**
     * Clear the bit in value
     *
     * @param v   the value
     * @param idx bit index, starts with 0, from low to high
     * @return new value with the bit cleared
     */
    public static long clear(long v, int idx) {
        return v & (~(1L << idx));
    }

    /**
     * Set the bit in value to the complement of current.
     *
     * @param v   the value
     * @param idx bit index, starts with 0, from low to high
     * @return new value with the bit flipped
     */
    public static long flip(long v, int idx) {
        return v ^ (1L << idx);
    }

    private static class LongMarksHolder {
        private static final long[] longMarks = {
                0, 1, 3, 7, 0xf,
                0x1f, 0x3f, 0x7f, 0xff,
                0x1ff, 0x3ff, 0x7ff, 0xfff,
                0x1fff, 0x3fff, 0x7fff, 0xffff,
                0x1ffff, 0x3ffff, 0x7ffff, 0xfffff,
                0x1fffff, 0x3fffff, 0x7fffff, 0xffffff,
                0x1ffffff, 0x3ffffff, 0x7ffffff, 0xfffffff,
                0x1fffffff, 0x3fffffff, 0x7fffffff, 0xffffffff,
                0x1ffffffffL, 0x3ffffffffL, 0x7ffffffffL, 0xfffffffffL,
                0x1fffffffffL, 0x3fffffffffL, 0x7fffffffffL, 0xffffffffffL,
                0x1ffffffffffL, 0x3ffffffffffL, 0x7ffffffffffL, 0xfffffffffffL,
                0x1fffffffffffL, 0x3fffffffffffL, 0x7fffffffffffL, 0xffffffffffffL,
                0x1ffffffffffffL, 0x3ffffffffffffL, 0x7ffffffffffffL, 0xfffffffffffffL,
                0x1fffffffffffffL, 0x3fffffffffffffL, 0x7fffffffffffffL, 0xffffffffffffffL,
                0x1ffffffffffffffL, 0x3ffffffffffffffL, 0x7ffffffffffffffL, 0xfffffffffffffffL,
                0x1fffffffffffffffL, 0x3fffffffffffffffL, 0x7fffffffffffffffL, 0xffffffffffffffffL,
        };
    }

    /**
     * Set bits of value, in range [from, to). The indexes are starts with 0, from low to high.
     *
     * @param v    the original value
     * @param from the from bit index, include
     * @param to   the to bit index, exclude
     * @return the value with bits set
     */
    public static long set(long v, int from, int to) {
        return v | (LongMarksHolder.longMarks[to - from] << from);
    }

    /**
     * Clear bits of value, in range [from, to). The indexes are starts with 0, from low to high.
     *
     * @param v    the original value
     * @param from the from bit index, include
     * @param to   the to bit index, exclude
     * @return the value with bits cleared
     */
    public static long clear(long v, int from, int to) {
        return v & (~(LongMarksHolder.longMarks[to - from] << from));
    }

    /**
     * Set bits of value, in range [from, to), to the complement of current values.
     * The indexes are starts with 0, from low to high.
     *
     * @param v    the original value
     * @param from the from bit index, include
     * @param to   the to bit index, exclude
     * @return the value with bits flipped
     */
    public static long flip(long v, int from, int to) {
        return v ^ (LongMarksHolder.longMarks[to - from] << from);
    }

    /**
     * Take bits of value, in range [from, to), to be a new value. The other bits of new value is zero.
     * The indexes are starts with 0, from low to high.
     *
     * @param v    the original value
     * @param from the from bit index, include
     * @param to   the to bit index, exclude
     * @return the new value
     */
    public static long take(long v, int from, int to) {
        return v & (LongMarksHolder.longMarks[to - from] << from);
    }

    /**
     * Take bits of value, in range [from, to), and then shift to lowest bits, to be a new value.
     * The other bits of new value is zero.
     * The indexes are starts with 0, from low to high.
     *
     * @param v    the original value
     * @param from the from bit index, include
     * @param to   the to bit index, exclude
     * @return the new value
     */
    public static long takeDown(long v, int from, int to) {
        return (v >>> from) & LongMarksHolder.longMarks[to - from];
    }
}
