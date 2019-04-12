package net.dongliu.commons;


import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.Arrays;

import static java.lang.Character.toLowerCase;
import static java.lang.Character.toUpperCase;
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
    public static String nullToEmpty(@Nullable String str) {
        return str == null ? "" : str;
    }

    /**
     * Parse str to int. If failed, return defaultValue.
     */
    public static int toInt(String str, int defaultValue) {
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException | NullPointerException e) {
            return defaultValue;
        }
    }

    /**
     * Parse str to long. If failed, return defaultValue.
     */
    public static long toLong(String str, long defaultValue) {
        try {
            return Long.parseLong(str);
        } catch (NumberFormatException | NullPointerException e) {
            return defaultValue;
        }
    }

    /**
     * Parse str to float. If failed, return defaultValue.
     */
    public static float toFloat(String str, float defaultValue) {
        try {
            return Float.parseFloat(str);
        } catch (NumberFormatException | NullPointerException e) {
            return defaultValue;
        }
    }

    /**
     * Parse str to double. If failed, return defaultValue.
     */
    public static double toDouble(String str, double defaultValue) {
        try {
            return Double.parseDouble(str);
        } catch (NumberFormatException | NullPointerException e) {
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
     * @deprecated use {@link Joiner}
     */
    @Deprecated
    public static String join(CharSequence prefix, CharSequence suffix, CharSequence delimiter,
                              Iterable<? extends CharSequence> strings) {
        requireNonNull(prefix);
        requireNonNull(suffix);
        requireNonNull(delimiter);
        requireNonNull(strings);
        return Joiner.of(prefix, suffix, delimiter).join(strings);
    }

    /**
     * Join strings, with prefix, suffix, and delimiter
     *
     * @param prefix    the prefix to prepend, can not be null
     * @param suffix    the suffix to append, can not be null
     * @param delimiter the delimiter to join multi string items, can not be null
     * @param strings   the string items
     * @return new string
     * @deprecated use {@link Joiner}
     */
    @Deprecated
    public static String join(CharSequence prefix, CharSequence suffix, CharSequence delimiter,
                              CharSequence... strings) {
        requireNonNull(prefix);
        requireNonNull(suffix);
        requireNonNull(delimiter);
        requireNonNull(strings);
        return Joiner.of(prefix, suffix, delimiter).join((Object[]) strings);
    }

    /**
     * Return a new string, which content is repeat times of origin str.
     *
     * @deprecated using {@link String#repeat(int)}
     */
    @Deprecated
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

    /**
     * Calculate the count of sub string. The sub string do not overlap.
     *
     * @param str can not be null
     * @param sub the sub string, can not be null or empty
     * @return the sub string occurred count
     */
    public static int countOf(String str, String sub) {
        return countOf(str, sub, false);
    }

    /**
     * Calculate the count of sub string.
     *
     * @param str     can not be null
     * @param sub     the sub string, can not be null or empty
     * @param overlap if the sub found in str can overlap
     * @return the sub string occurred count
     */
    public static int countOf(String str, String sub, boolean overlap) {
        requireNonNull(str);
        requireNonNull(sub);
        if (sub.isEmpty()) {
            throw new IllegalArgumentException("sub string cannot be empty");
        }
        int count = 0;
        int offset = 0;
        int index;
        while ((index = str.indexOf(sub, offset)) != -1) {
            if (overlap) {
                offset = index + 1;
            } else {
                offset = index + sub.length();
            }
            count++;
        }
        return count;
    }

    /**
     * @deprecated using {@link #removeSuffix(String, String)}
     */
    @Deprecated
    public static String trimSuffix(String str, String suffix) {
        return removeSuffix(str, suffix);
    }

    /**
     * @deprecated using {@link #removePrefix(String, String)}
     */
    @Deprecated
    public static String trimPrefix(String str, String prefix) {
        return removePrefix(str, prefix);
    }

    /**
     * If str end with suffix, remove suffix
     *
     * @param str    string
     * @param suffix suffix
     * @return str without suffix
     */
    public static String removeSuffix(String str, String suffix) {
        requireNonNull(str);
        requireNonNull(suffix);
        if (str.endsWith(suffix)) {
            return str.substring(0, str.length() - suffix.length());
        }
        return str;
    }

    /**
     * If str start with prefix, remove prefix
     *
     * @param str    string
     * @param prefix suffix
     * @return str without prefix
     */
    public static String removePrefix(String str, String prefix) {
        requireNonNull(str);
        requireNonNull(prefix);
        if (str.startsWith(prefix)) {
            return str.substring(prefix.length());
        }
        return str;
    }

    /**
     * If str start with prefix, remove prefix; If str end with suffix, remove suffix;
     * If prefix and suffix overlapped, return empty str.
     *
     * @param str    string
     * @param prefix suffix
     * @param suffix suffix
     * @return str without prefix and suffix
     */
    public static String removePrefixAndSuffix(String str, String prefix, String suffix) {
        requireNonNull(str);
        requireNonNull(prefix);
        requireNonNull(suffix);
        if (str.isEmpty()) {
            return str;
        }
        int start = 0;
        if (str.startsWith(prefix)) {
            start = prefix.length();
        }
        int end = str.length();
        if (str.endsWith(suffix)) {
            end = str.length() - suffix.length();
        }
        if (start >= end) {
            return "";
        }
        return str.substring(start, end);
    }

    /**
     * Remove string prefix with len. if len larger than str size, return empty string.
     *
     * @param str the string
     * @param len the prefix len to remove
     * @return string without prefix
     */
    public static String removePrefix(String str, int len) {
        requireNonNull(str);
        Preconditions.assertArgument(len >= 0, () -> "invalid len: " + len);
        if (len >= str.length()) {
            return "";
        }
        return str.substring(len);
    }

    /**
     * Remove string suffix with len. if len larger than str size, return empty string.
     *
     * @param str the string
     * @param len the suffix len to remove
     * @return string without suffix
     */
    public static String removeSuffix(String str, int len) {
        requireNonNull(str);
        Preconditions.assertArgument(len >= 0, () -> "invalid len: " + len);
        if (len >= str.length()) {
            return "";
        }
        return str.substring(0, str.length() - len);
    }

    /**
     * Return str, with first char is uppercase. If the first char of original str is already uppercase, or str is empty, return str self.
     * Note: this method do not handle surrogate correctly.
     *
     * @param str the original str
     * @return new string with first char uppercase
     */
    public static String capitalize(String str) {
        requireNonNull(str);
        if (str.isEmpty()) {
            return str;
        }
        char c = str.charAt(0);
        if (Character.isUpperCase(c)) {
            return str;
        }
        return toUpperCase(c) + str.substring(1);
    }

    /**
     * Return str, with first char is lowercase. If the first char of original str is already lowercase, or str is empty, return str self.
     * Note: this method do not handle surrogate correctly.
     *
     * @param str the original str
     * @return new string with first char lowercase
     */
    public static String deCapitalize(String str) {
        requireNonNull(str);
        if (str.isEmpty()) {
            return str;
        }
        char c = str.charAt(0);
        if (Character.isLowerCase(c)) {
            return str;
        }
        return toLowerCase(c) + str.substring(1);
    }

    /**
     * Pad the string, at left, to len, with padding char. If str size already equals or larger than len, return string self.
     *
     * @param str     the str to be pad
     * @param len     the desired len of padded str
     * @param padding the padding char
     * @return padded str
     */
    public static String padLeft(String str, int len, char padding) {
        requireNonNull(str);
        if (str.length() >= len) {
            return str;
        }
        char[] chars = new char[len];
        Arrays.fill(chars, 0, len - str.length(), padding);
        str.getChars(0, str.length(), chars, len - str.length());
        return new String(chars);
    }

    /**
     * Pad the string, at right, to len, with padding char. If str size already equals or larger than len, return string self.
     *
     * @param str     the str to be pad
     * @param len     the desired len of padded str
     * @param padding the padding char
     * @return padded str
     */
    public static String padRight(String str, int len, char padding) {
        requireNonNull(str);
        if (str.length() >= len) {
            return str;
        }
        char[] chars = new char[len];
        str.getChars(0, str.length(), chars, 0);
        Arrays.fill(chars, str.length(), len, padding);
        return new String(chars);
    }

    /**
     * Return a centered string of length width.Padding is done using the specified padding character.
     *
     * @param str     the str to be pad
     * @param len     the desired len of padded str
     * @param padding the padding char
     * @return padded str
     */
    public static String padToCenter(String str, int len, char padding) {
        requireNonNull(str);
        if (str.length() >= len) {
            return str;
        }
        int toBePad = len - str.length();
        int left = toBePad / 2;
        int right = toBePad - left;
        char[] chars = new char[len];
        Arrays.fill(chars, 0, left, padding);
        str.getChars(0, str.length(), chars, left);
        Arrays.fill(chars, len - right, len, padding);
        return new String(chars);
    }

    /**
     * Join string lines with '\n'
     */
    public static String joinLines(Iterable<? extends CharSequence> strings) {
        StringBuilder sb = new StringBuilder();
        for (CharSequence string : strings) {
            sb.append(string).append("\n");
        }
        return sb.toString();
    }

    /**
     * If str do not has specific prefix, return a new String prepending the prefix; return str its self otherwise.
     */
    public static String prependIfMissing(String str, String prefix) {
        requireNonNull(str);
        requireNonNull(prefix);
        if (str.isEmpty()) {
            return prefix;
        }
        if (!str.startsWith(prefix)) {
            return prefix + str;
        }
        return str;
    }

    /**
     * If str do not has specific suffix, return a new String appending the suffix; return str its self otherwise.
     */
    public static String appendIfMissing(String str, String suffix) {
        requireNonNull(str);
        requireNonNull(suffix);
        if (str.isEmpty()) {
            return suffix;
        }
        if (!str.endsWith(suffix)) {
            return str + suffix;
        }
        return str;
    }
}
