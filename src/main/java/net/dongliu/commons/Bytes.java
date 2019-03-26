package net.dongliu.commons;

import static java.util.Objects.requireNonNull;

/**
 * Byte array utils
 */
public class Bytes {

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
}
