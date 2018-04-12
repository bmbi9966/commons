package net.dongliu.commons;

import java.util.Objects;

/**
 * Utils methods for String
 */
public class Strings {

    /**
     * Parse str to int. If failed, return defaultValue.
     */
    public static int toInt(String str, int defaultValue) {
        try {
            return Integer.parseInt(str);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * Parse str to long. If failed, return defaultValue.
     */
    public static long toLong(String str, long defaultValue) {
        try {
            return Long.parseLong(str);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * Parse str to float. If failed, return defaultValue.
     */
    public static float toFloat(String str, float defaultValue) {
        try {
            return Float.parseFloat(str);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * Parse str to double. If failed, return defaultValue.
     */
    public static double toDouble(String str, double defaultValue) {
        try {
            return Double.parseDouble(str);
        } catch (Exception e) {
            return defaultValue;
        }
    }

    /**
     * Join strings, with prefix, suffix, and delimiter
     *
     * @param prefix    the prefix to prepend, can not be null
     * @param suffix    the suffix to append, can not be null
     * @param delimiter the delimiter to join multi string items, can not be null
     * @param strings   the string items
     * @return new string
     */
    public static String join(CharSequence prefix, CharSequence suffix, CharSequence delimiter,
                              Iterable<? extends CharSequence> strings) {
        Objects.requireNonNull(prefix);
        Objects.requireNonNull(suffix);
        Objects.requireNonNull(delimiter);
        Objects.requireNonNull(strings);
        StringBuilder sb = new StringBuilder();
        sb.append(prefix);
        boolean flag = false;
        for (CharSequence str : strings) {
            sb.append(str).append(delimiter);
            if (!flag) {
                flag = true;
            }
        }
        if (flag) {
            sb.setLength(sb.length() - delimiter.length());
        }
        sb.append(suffix);
        return sb.toString();
    }

    /**
     * Join strings, with prefix, suffix, and delimiter
     *
     * @param prefix    the prefix to prepend, can not be null
     * @param suffix    the suffix to append, can not be null
     * @param delimiter the delimiter to join multi string items, can not be null
     * @param strings   the string items
     * @return new string
     */
    public static String join(CharSequence prefix, CharSequence suffix, CharSequence delimiter,
                              CharSequence... strings) {
        Objects.requireNonNull(prefix);
        Objects.requireNonNull(suffix);
        Objects.requireNonNull(delimiter);
        Objects.requireNonNull(strings);
        StringBuilder sb = new StringBuilder();
        sb.append(prefix);
        for (int i = 0; i < strings.length; i++) {
            sb.append(strings[i]);
            if (i != strings.length - 1) {
                sb.append(delimiter);
            }
        }
        sb.append(suffix);
        return sb.toString();
    }

    /**
     * Return a new string, which content is repeat times of origin str.
     */
    public static String repeat(String str, int times) {
        Objects.requireNonNull(str);
        if (times < 0) {
            throw new IllegalArgumentException("repeat count less then zero");
        }

        if (str.isEmpty() || times == 0) {
            return "";
        }

        StringBuilder sb = new StringBuilder(str.length() * times);
        for (int i = 0; i < times; i++) {
            sb.append(str);
        }
        return sb.toString();
    }


}
