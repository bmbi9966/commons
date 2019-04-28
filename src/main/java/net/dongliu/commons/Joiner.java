package net.dongliu.commons;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.StringJoiner;

import static java.util.Objects.requireNonNull;

/**
 * For join strings.
 * This class is immutable, and should be reused across multi invocations of join with same rule.
 */
public class Joiner {
    // the prefix to prepend before target string
    private final CharSequence prefix;
    // the suffix to append after target string
    private final CharSequence suffix;
    // the delimiter to join multi string items
    private final CharSequence delimiter;
    // skip null values
    private final boolean skipNulls;
    // use empty string for null values. If skipNulls is set, this setting would not take effect.
    private final boolean nullToEmpty;
    // skip empty string
    private final boolean skipEmpty;

    private Joiner(CharSequence prefix, CharSequence suffix, CharSequence delimiter, boolean skipNulls,
                   boolean nullToEmpty, boolean skipEmpty) {
        this.prefix = prefix;
        this.suffix = suffix;
        this.delimiter = delimiter;
        this.skipNulls = skipNulls;
        this.nullToEmpty = nullToEmpty;
        this.skipEmpty = skipEmpty;
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
                false, false, false);
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
     * Return a new Builder instance with default initial settings.
     */
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Join strings, with prefix, suffix, and delimiter
     *
     * @param values the string items
     * @return new string
     */
    public String join(@NonNull Iterable<@Nullable ?> values) {
        requireNonNull(values);
        StringJoiner joiner = new StringJoiner(delimiter, prefix, suffix);
        for (Object o : values) {
            if (o == null) {
                if (skipNulls) {
                    continue;
                }
                if (nullToEmpty) {
                    o = "";
                }
            }
            String str = String.valueOf(o);
            if (skipEmpty && str.isEmpty()) {
                continue;
            }
            joiner.add(str);
        }
        return joiner.toString();
    }

    /**
     * Join strings, with prefix, suffix, and delimiter
     *
     * @param values the string items
     * @return new string
     */
    public String join(@Nullable Object @NonNull ... values) {
        requireNonNull(values);
        StringJoiner joiner = new StringJoiner(delimiter, prefix, suffix);
        for (Object o : values) {
            if (o == null) {
                if (skipNulls) {
                    continue;
                }
                if (nullToEmpty) {
                    o = "";
                }
            }
            String str = String.valueOf(o);
            if (skipEmpty && str.isEmpty()) {
                continue;
            }
            joiner.add(str);
        }
        return joiner.toString();
    }


    public static final class Builder {
        private CharSequence prefix = "";
        private CharSequence suffix = "";
        private CharSequence delimiter = "";
        private boolean skipNulls = false;
        private boolean nullToEmpty = false;
        private boolean skipEmpty = false;

        private Builder() {
        }

        /**
         * Set prefix for joined string result. Default is empty str.
         */
        public Builder prefix(CharSequence prefix) {
            this.prefix = requireNonNull(prefix);
            return this;
        }

        /**
         * Set suffix for joined string result. Default is empty str.
         */
        public Builder suffix(CharSequence suffix) {
            this.suffix = suffix;
            return this;
        }

        /**
         * Set delimiter for joined string result. Default is empty str.
         */
        public Builder delimiter(CharSequence delimiter) {
            this.delimiter = delimiter;
            return this;
        }

        /**
         * If a element to be joined is null, skip this element.
         *
         * @return new Joiner
         */
        public Builder skipNulls() {
            this.skipNulls = true;
            return this;
        }

        /**
         * If a element to be joined is null, treat as empty str instead of "null".
         * This setting works only when {@link #skipNulls} is false
         *
         * @return new Joiner
         */
        public Builder nullToEmpty() {
            this.nullToEmpty = true;
            return this;
        }

        /**
         * If a element's toString result is empty str, the skip this element.
         * Set skipEmpty to true would not skip null values, but if {@link #nullToEmpty} is be set also, the null values would be skipped.
         */
        public Builder skipEmpty() {
            this.skipEmpty = true;
            return this;
        }

        /**
         * Build a new Joiner instance with settings.
         */
        public Joiner build() {
            return new Joiner(prefix, suffix, delimiter, skipNulls, nullToEmpty, skipEmpty);
        }
    }
}
