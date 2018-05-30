package net.dongliu.commons.regex;

import net.dongliu.commons.collection.Iterators;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

/**
 * Utils method for pattern
 */
public class Patterns {

    /**
     * Match the text with pattern, wrap the match result as Stream.
     *
     * @param pattern the regex pattern to find matcher
     * @param text    the target text
     * @return matched string stream
     */
    public static Stream<String> matched(Pattern pattern, String text) {
        return matched(pattern, text, 0);
    }

    /**
     * Match the text with pattern, wrap the match result as Stream.
     *
     * @param pattern    the regex pattern to find matcher
     * @param text       the target text
     * @param groupIndex the index of a capturing group in this matcher's pattern
     * @return matched string stream
     */
    public static Stream<String> matched(Pattern pattern, String text, int groupIndex) {
        requireNonNull(pattern);
        requireNonNull(text);
        Matcher matcher = pattern.matcher(text);
        Iterator<String> iterator = new MatcherIterator(matcher, groupIndex);
        return Iterators.stream(iterator);
    }

    /**
     * Match the text with pattern, get all matched group as strings.
     *
     * @param pattern the regex pattern to find matcher
     * @param text    the target text
     * @return matched strings
     */
    public static List<String> getAllMatched(Pattern pattern, String text) {
        return getAllMatched(pattern, text, 0);
    }

    /**
     * Match the text with pattern, get all matched group as strings.
     *
     * @param pattern    the regex pattern to find matcher
     * @param text       the target text
     * @param groupIndex the index of a capturing group in this matcher's pattern
     * @return matched strings
     */
    public static List<String> getAllMatched(Pattern pattern, String text, int groupIndex) {
        requireNonNull(pattern);
        requireNonNull(text);
        Matcher matcher = pattern.matcher(text);
        List<String> list = new ArrayList<>();
        while (matcher.find()) {
            String str = matcher.group(groupIndex);
            list.add(str);
        }
        return list;
    }

    /**
     * Match the text with pattern, get all matched group as strings.
     *
     * @param pattern   the regex pattern to find matcher
     * @param text      the target text
     * @param groupName the name of a named-capturing group in this matcher's pattern
     * @return matched strings
     */
    public static List<String> getAllMatched(Pattern pattern, String text, String groupName) {
        requireNonNull(pattern);
        requireNonNull(text);
        Matcher matcher = pattern.matcher(text);
        List<String> list = new ArrayList<>();
        while (matcher.find()) {
            String str = matcher.group(groupName);
            list.add(str);
        }
        return list;
    }

    private static class MatcherIterator implements Iterator<String> {
        private final Matcher matcher;
        private final int group;

        private boolean checked;
        private boolean hasNext;

        private MatcherIterator(Matcher matcher, int group) {
            this.matcher = matcher;
            this.group = group;
        }

        @Override
        public boolean hasNext() {
            if (checked) {
                return hasNext;
            }
            hasNext = matcher.find();
            checked = true;
            return hasNext;
        }

        @Override
        public String next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            String str = matcher.group(group);
            checked = false;
            return str;
        }
    }
}
