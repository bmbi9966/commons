package net.dongliu.commons;

import java.util.function.Supplier;

import static java.util.Objects.requireNonNull;

/**
 * Utils methods for checking params
 */
public class Preconditions {

    /**
     * Check sub range of Array/String/List.
     *
     * @param length len of the original Array/String/List. we think this length always larger than or equal with zero.
     * @param offset the offset of sub sequence
     * @param size   the size of sub sequence
     * @throws IndexOutOfBoundsException if index range not in Array/String/List
     */
    public static void checkSubRange(int length, int offset, int size) throws IndexOutOfBoundsException {
        if (length < 0) {
            throw new IllegalArgumentException("length cannot less than zero");
        }
        if (offset < 0 || size < 0) {
            throw new IndexOutOfBoundsException("Illegal sub sequence range, offset: " + offset + ", size: " + size);
        }
        if (size > length - offset) {
            throw new IndexOutOfBoundsException("Illegal sub sequence range, length: " + length
                    + ", offset: " + offset + ", size: " + size);
        }
    }

    /**
     * Check array and it's range.
     *
     * @param offset the offset of sub sequence
     * @param size   the size of sub sequence
     * @throws IndexOutOfBoundsException if index range not in Array/String/List
     */
    public static void checkArrayAndRange(byte[] array, int offset, int size) throws IndexOutOfBoundsException {
        requireNonNull(array);
        checkSubRange(array.length, offset, size);
    }

    /**
     * Check array and it's range.
     *
     * @param offset the offset of sub sequence
     * @param size   the size of sub sequence
     * @throws IndexOutOfBoundsException if index range not in Array/String/List
     */
    public static void checkArrayAndRange(char[] array, int offset, int size) throws IndexOutOfBoundsException {
        requireNonNull(array);
        checkSubRange(array.length, offset, size);
    }

    /**
     * Check array and it's range.
     *
     * @param offset the offset of sub sequence
     * @param size   the size of sub sequence
     * @throws IndexOutOfBoundsException if index range not in Array/String/List
     */
    public static <T> void checkArrayAndRange(T[] array, int offset, int size) throws IndexOutOfBoundsException {
        requireNonNull(array);
        checkSubRange(array.length, offset, size);
    }

    /**
     * Check if argument meet condition. If failed, throw IllegalArgumentException with message.
     *
     * @throws IllegalArgumentException
     */
    public static void assertArgument(boolean result, Supplier<String> messageSupplier) throws IllegalArgumentException {
        if (!result) {
            throw new IllegalArgumentException(messageSupplier.get());
        }
    }
}
