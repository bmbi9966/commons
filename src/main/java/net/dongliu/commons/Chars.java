package net.dongliu.commons;

import static java.util.Objects.requireNonNull;

/**
 * Char array utils
 */
public class Chars {

    /**
     * Convenient method to make a char array
     */
    public static char[] array(char... chars) {
        return chars;
    }

    /**
     * Merge char array
     *
     * @return a new array
     */
    public static char[] concat(char[] array, char... chars) {
        requireNonNull(array);
        requireNonNull(chars);

        char[] newArray = new char[array.length + chars.length];
        System.arraycopy(array, 0, newArray, 0, array.length);
        System.arraycopy(chars, 0, newArray, array.length, chars.length);
        return newArray;
    }
}
