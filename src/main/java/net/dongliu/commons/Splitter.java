package net.dongliu.commons;

import net.dongliu.commons.collection.Iterators;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

/**
 * For splitting strings, with plain raw string or regular expression delimiter.
 * This class is immutable, should be reused if possible.
 */
public class Splitter {

    @Nullable
    private final String delimiter;
    @Nullable
    private final Pattern delimiterPattern;
    private final boolean trimResults;
    private final boolean skipEmpty;
    private final String prefix;
    private final String suffix;

    public Splitter(String delimiter, Pattern delimiterPattern, boolean trimResults, boolean skipEmpty,
                    String prefix, String suffix) {
        this.delimiter = delimiter;
        this.delimiterPattern = delimiterPattern;
        this.trimResults = trimResults;
        this.skipEmpty = skipEmpty;
        this.prefix = prefix;
        this.suffix = suffix;
    }

    /**
     * Create a splitter which split string by plain raw string delimiter.
     */
    public static Splitter of(String delimiter) {
        requireNonNull(delimiter);
        return new Splitter(delimiter, null, false, false, "", "");
    }

    /**
     * Create a splitter which split string by regular expression delimiter.
     */
    public static Splitter ofRegex(Pattern pattern) {
        requireNonNull(pattern);
        return new Splitter(null, pattern, false, false, "", "");
    }

    /**
     * Create a splitter which split string by regular expression delimiter.
     */
    public static Splitter ofRegex(String pattern) {
        requireNonNull(pattern);
        return ofRegex(Pattern.compile(pattern));
    }

    /**
     * Create a builder using plain str delimiter.
     */
    public static Builder builder(String delimiter) {
        requireNonNull(delimiter);
        return new Builder(delimiter);
    }

    /**
     * Create a builder using regex delimiter.
     */
    public static Builder regexBuilder(Pattern pattern) {
        requireNonNull(pattern);
        return new Builder(pattern);
    }

    /**
     * Create a builder using regex delimiter.
     */
    public static Builder regexBuilder(String pattern) {
        requireNonNull(pattern);
        return new Builder(Pattern.compile(pattern));
    }

    /**
     * Split the str as a String {@link Stream}.
     */
    public Stream<String> split(String str) {
        requireNonNull(str);
        if (str.isEmpty()) {
            return skipEmpty ? Stream.empty() : Stream.of(str);
        }
        var iterator = createSplitIterator(str);
        return Iterators.stream(iterator);
    }

    /**
     * Split the str to a {@link RandomAccess} String {@link List}.
     * There is no guaranty for the immutability of returned List.
     */
    public List<String> splitToList(String str) {
        requireNonNull(str);
        if (str.isEmpty()) {
            return skipEmpty ? List.of() : List.of(str);
        }
        var iterator = createSplitIterator(str);
        if (!iterator.hasNext()) {
            return List.of();
        }
        var list = new ArrayList<String>();
        while (iterator.hasNext()) {
            list.add(iterator.next());
        }
        return list;
    }

    private SplitIterator createSplitIterator(String str) {
        int from;
        if (!prefix.isEmpty() && str.startsWith(prefix)) {
            from = prefix.length();
        } else {
            from = 0;
        }
        int to;
        if (!suffix.isEmpty() && str.endsWith(suffix)) {
            to = str.length() - suffix.length();
        } else {
            to = str.length();
        }
        if (from >= to) {
            str = "";
            from = to = 0;
        }
        if (delimiterPattern != null) {
            return new RegexSplitIterator(str, from, to, delimiterPattern, trimResults, skipEmpty);
        }
        if (delimiter != null) {
            return new PlainSplitIterator(str, from, to, delimiter, trimResults, skipEmpty);
        }
        throw new IllegalStateException();
    }

    private abstract static class SplitIterator implements Iterator<String> {
        private final String str;
        private final int from; // inclusive
        private final int to; // exclusive
        private final boolean trimResults;
        private final boolean skipEmpty;
        private int pos;
        private String next;

        private SplitIterator(String str, int from, int to, boolean trimResults, boolean skipEmpty) {
            this.str = str;
            this.from = from;
            this.to = to;
            this.trimResults = trimResults;
            this.skipEmpty = skipEmpty;
            this.pos = from;
        }

        @Override
        public boolean hasNext() {
            if (next != null) {
                return true;
            }
            do {
                next = null;
                calculateNext();
                if (next == null) {
                    return false;
                }
                if (trimResults) {
                    next = next.trim();
                }
            } while (skipEmpty && next.isEmpty());
            return true;
        }

        @Override
        public String next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            String s = next;
            next = null;
            return s;
        }

        private void calculateNext() {
            if (pos > to) {
                return;
            }
            long l = findNext(str, pos);
            int index = (int) (l >>> 32);
            int delimiterLen = (int) l;
            if (index == -1) {
                index = to;
            }
            if (delimiterLen > 0) {
                next = str.substring(pos, index);
                pos = index + delimiterLen;
            } else {
                if (pos < to) {
                    next = str.substring(pos, pos + 1);
                    pos = index + 1;
                } else {
                    pos = index + 1;
                }
            }
        }

        protected abstract long findNext(String str, int from);
    }

    private static class PlainSplitIterator extends SplitIterator {
        private final String delimiter;

        private PlainSplitIterator(String str, int from, int to, String delimiter,
                                   boolean trimResults, boolean skipEmpty) {
            super(str, from, to, trimResults, skipEmpty);
            this.delimiter = delimiter;
        }


        @Override
        protected long findNext(String str, int from) {
            int index = str.indexOf(delimiter, from);
            return (long) index << 32 | delimiter.length();
        }
    }

    private static class RegexSplitIterator extends SplitIterator {
        private final Matcher matcher;

        private RegexSplitIterator(String str, int from, int to, Pattern pattern,
                                   boolean trimResults, boolean skipEmpty) {
            super(str, from, to, trimResults, skipEmpty);
            this.matcher = pattern.matcher(str.substring(from, to));
        }


        @Override
        protected long findNext(String str, int from) {
            if (matcher.find()) {
                return (long) matcher.start() << 32 | (matcher.end() - matcher.start());
            }
            return -1L << 32 | 1;
        }
    }

    public static final class Builder {
        private @Nullable String delimiter;
        private @Nullable Pattern delimiterPattern;
        private boolean trimResults;
        private boolean skipEmpty;
        private String prefix = "";
        private String suffix = "";

        private Builder(String delimiter) {
            this.delimiter = delimiter;
        }

        private Builder(Pattern pattern) {
            this.delimiterPattern = pattern;
        }


        /**
         * Trim split results
         */
        public Builder trimResults() {
            this.trimResults = true;
            return this;
        }

        /**
         * Skip empty results. If {@link #trimResults} is enabled, would skip blank result too.
         */
        public Builder skipEmpty() {
            this.skipEmpty = true;
            return this;
        }

        /**
         * When do split, skip the prefix of given string if it has the prefix
         */
        public Builder prefix(String prefix) {
            this.prefix = prefix;
            return this;
        }

        /**
         * When do split, skip the suffix of given string if it has the suffix
         */
        public Builder suffix(String suffix) {
            this.suffix = suffix;
            return this;
        }

        public Splitter build() {
            return new Splitter(delimiter, delimiterPattern, trimResults, skipEmpty, prefix, suffix);
        }
    }
}
