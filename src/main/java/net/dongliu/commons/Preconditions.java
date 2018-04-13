package net.dongliu.commons;

/**
 * Utils methods for checking params
 */
public class Preconditions {

    /**
     * Check sub range of Array/String/List.
     *
     * @param length len of the original Array/String/List
     * @param offset the offset of sub sequence
     * @param size   the size of sub sequence
     * @throws IndexOutOfBoundsException if index range not in Array/String/List
     */
    public static void checkSubRange(int length, int offset, int size) throws IndexOutOfBoundsException {
        if (length < 0 || offset < 0 || size < 0) {
            throw new IndexOutOfBoundsException("Illegal sub sequence range, length: " + length
                    + ", offset: " + offset + ", size: " + size);
        }
        if (size > length - offset) {
            throw new IndexOutOfBoundsException("Illegal sub sequence range, length: " + length
                    + ", offset: " + offset + ", size: " + size);
        }
    }
}
