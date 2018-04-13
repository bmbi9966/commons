package net.dongliu.commons;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static java.util.Objects.requireNonNull;

/**
 * Utils methods for String
 */
public class Strings {

    /**
     * If str is null, return empty str; else return str self.
     *
     * @param str the string
     * @return nonNull string
     */
    @Nonnull
    public static String nullToEmpty(@Nullable String str) {
        return str == null ? "" : str;
    }

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
        requireNonNull(prefix);
        requireNonNull(suffix);
        requireNonNull(delimiter);
        requireNonNull(strings);
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
        requireNonNull(prefix);
        requireNonNull(suffix);
        requireNonNull(delimiter);
        requireNonNull(strings);
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
        requireNonNull(str);
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

    /**
     * Return subString before first matched separator. If str does not contains sep, return the origin string.
     *
     * @param str the origin str. can not be null
     * @param sep the separator to get sub string. cannot be null or empty
     * @return sub string
     */
    public static String subStringBefore(String str, String sep) {
        requireNonNull(str);
        requireNonNull(sep);
        if (sep.isEmpty()) {
            throw new IllegalArgumentException("separator cannot be empty");
        }
        int index = str.indexOf(sep);
        if (index < 0) {
            return str;
        }
        return str.substring(0, index);
    }

    /**
     * Return subString before last matched separator. If str does not contains sep, return the origin string.
     *
     * @param str the origin str. can not be null
     * @param sep the separator to get sub string. cannot be null or empty
     * @return sub string
     */
    public static String subStringBeforeLast(String str, String sep) {
        requireNonNull(str);
        requireNonNull(sep);
        if (sep.isEmpty()) {
            throw new IllegalArgumentException("separator cannot be empty");
        }
        int index = str.lastIndexOf(sep);
        if (index < 0) {
            return str;
        }
        return str.substring(0, index);
    }

    /**
     * Return subString after first matched separator. If str does not contains sep, return the origin string.
     *
     * @param str the origin str. can not be null
     * @param sep the separator to get sub string. cannot be null or empty
     * @return sub string
     */
    public static String subStringAfter(String str, String sep) {
        requireNonNull(str);
        requireNonNull(sep);
        if (sep.isEmpty()) {
            throw new IllegalArgumentException("separator cannot be empty");
        }
        int index = str.indexOf(sep);
        if (index < 0) {
            return str;
        }
        return str.substring(index + sep.length());
    }

    /**
     * Return subString after last separator. If str does not contains sep, return the origin string.
     *
     * @param str the origin str. can not be null
     * @param sep the separator to get sub string. cannot be null or empty
     * @return sub string
     */
    public static String subStringAfterLast(String str, String sep) {
        requireNonNull(str);
        requireNonNull(sep);
        if (sep.isEmpty()) {
            throw new IllegalArgumentException("separator cannot be empty");
        }
        int index = str.lastIndexOf(sep);
        if (index < 0) {
            return str;
        }
        return str.substring(index + sep.length());
    }
}
