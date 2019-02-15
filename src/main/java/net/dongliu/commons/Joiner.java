package net.dongliu.commons;

import net.dongliu.commons.annotation.Nullable;

import java.util.StringJoiner;

import static java.util.Objects.requireNonNull;

/**
 * For join strings
 */
public class Joiner {
    // the prefix to prepend before target string
    private final String prefix;
    // the suffix to append after target string
    private final String suffix;
    // the delimiter to join multi string items
    private final String delimiter;
    // skip null values
    private final boolean skipNulls;
    // use empty string for null values. If skipNulls is set, this setting would not take effect.
    private final boolean nullToEmpty;

    private Joiner(CharSequence prefix, CharSequence suffix, CharSequence delimiter, boolean skipNulls,
                   boolean nullToEmpty) {
        this.prefix = prefix.toString();
        this.suffix = suffix.toString();
        this.delimiter = delimiter.toString();
        this.skipNulls = skipNulls;
        this.nullToEmpty = nullToEmpty;
    }

    /**
     * Create new String Joiner.
     *
     * @param prefix    the prefix to prepend, can not be null
     * @param suffix    the suffix to append, can not be null
     * @param delimiter the delimiter to join multi string items, can not be null
     * @return Joiner
     */
    public static Joiner of(CharSequence prefix, CharSequence suffix, CharSequence delimiter) {
        return new Joiner(requireNonNull(prefix), requireNonNull(suffix), requireNonNull(delimiter),
                false, false);
    }

    /**
     * Create new String Joiner.
     *
     * @param delimiter the delimiter to join multi string items, can not be null
     * @return Joiner
     */
    public static Joiner of(CharSequence delimiter) {
        return of("", "", delimiter);
    }

    /**
     * Join strings, with prefix, suffix, and delimiter
     *
     * @param values the string items
     * @return new string
     */
    public String join(Iterable<@Nullable ?> values) {
        StringJoiner joiner = new StringJoiner(delimiter, prefix, suffix);
        for (Object o : values) {
            if (o == null) {
                if (skipNulls) {
                    continue;
                }
                if (nullToEmpty) {
                    joiner.add("");
                    continue;
                }
            }
            joiner.add(String.valueOf(o));
        }
        return joiner.toString();
    }

    /**
     * Join strings, with prefix, suffix, and delimiter
     *
     * @param values the string items
     * @return new string
     */
    public String join(@Nullable Object... values) {
        StringJoiner joiner = new StringJoiner(delimiter, prefix, suffix);
        for (Object o : values) {
            if (o == null) {
                if (skipNulls) {
                    continue;
                }
                if (nullToEmpty) {
                    joiner.add("");
                    continue;
                }
            }
            joiner.add(String.valueOf(o));
        }
        return joiner.toString();
    }

    /**
     * Return a new Joiner, which skipNulls is set.
     *
     * @param skipNulls if skip nulls, default false
     * @return new Joiner
     */
    public Joiner skipNulls(boolean skipNulls) {
        return new Joiner(prefix, suffix, delimiter, skipNulls, nullToEmpty);
    }

    /**
     * Return a new Joiner, which nullToEmpty is set.
     *
     * @param nullToEmpty If treat null as empty string. Default false.
     *                    This will not take effect if skipNulls is set to true
     * @return new Joiner
     */
    public Joiner nullToEmpty(boolean nullToEmpty) {
        return new Joiner(prefix, suffix, delimiter, skipNulls, nullToEmpty);
    }
}
