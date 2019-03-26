package net.dongliu.commons;

import static java.util.Objects.requireNonNull;

/**
 * Char array utils
 */
public class Ints {

    /**
     * Convenient method to make a int array
     */
    public static int[] array(int... values) {
        return values;
    }

    /**
     * Merge int array
     *
     * @return a new array
     */
    public static int[] concat(int[] array, int... values) {
        requireNonNull(array);
        requireNonNull(values);

        int[] newArray = new int[array.length + values.length];
        System.arraycopy(array, 0, newArray, 0, array.length);
        System.arraycopy(values, 0, newArray, array.length, values.length);
        return newArray;
    }
}
