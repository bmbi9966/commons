package net.dongliu.commons;

import static java.util.Objects.requireNonNull;

/**
 * Byte array utils
 */
public class Bytes {

    public static byte[] empty = {};

    /**
     * Convenient method to make a byte array
     */
    public static byte[] array(byte... bytes) {
        return bytes;
    }

    /**
     * Merge byte array
     *
     * @return a new array
     */
    public static byte[] concat(byte[] array, byte... bytes) {
        requireNonNull(array);
        requireNonNull(bytes);

        byte[] newArray = new byte[array.length + bytes.length];
        System.arraycopy(array, 0, newArray, 0, array.length);
        System.arraycopy(bytes, 0, newArray, array.length, bytes.length);
        return newArray;
    }

    /**
     * Merge byte arrays
     *
     * @return a new array
     */
    public static byte[] concat(byte[]... arrays) {
        int totalLen = 0;
        for (byte[] array : arrays) {
            totalLen = Math.addExact(totalLen, array.length);
        }
        byte[] bytes = new byte[totalLen];
        int offset = 0;
        for (byte[] array : arrays) {
            System.arraycopy(array, 0, bytes, offset, array.length);
            offset += array.length;
        }
        return bytes;
    }
}
