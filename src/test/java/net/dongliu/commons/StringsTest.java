package net.dongliu.commons;

import net.dongliu.commons.collection.Lists;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class StringsTest {

    @Test
    public void nullToEmpty() {
        assertEquals("", Strings.nullToEmpty(null));
        assertEquals("", Strings.nullToEmpty(""));
        assertEquals("1", Strings.nullToEmpty("1"));
    }

    @Test
    public void repeat() {
        assertEquals("sasa", Strings.repeat("sa", 2));
        assertEquals("", Strings.repeat("", 2));
        assertEquals("", Strings.repeat("ss", 0));
    }

    @Test
    @SuppressWarnings("deprecation")
    public void join() {
        assertEquals("1, 2, 3", Strings.join("", "", ", ", Lists.of("1", "2", "3")));
        assertEquals("(1, 2, 3)", Strings.join("(", ")", ", ", Lists.of("1", "2", "3")));
        assertEquals("(1)", Strings.join("(", ")", ", ", Lists.of("1")));
        assertEquals("1)", Strings.join("", ")", ", ", Lists.of("1")));
        assertEquals("1", Strings.join("", "", ", ", Lists.of("1")));
        assertEquals("", Strings.join("", "", ", ", Lists.<String>of()));

        assertEquals("1, 2, 3", Strings.join("", "", ", ", "1", "2", "3"));
        assertEquals("(1, 2, 3)", Strings.join("(", ")", ", ", "1", "2", "3"));
        assertEquals("(1)", Strings.join("(", ")", ", ", "1"));
        assertEquals("1)", Strings.join("", ")", ", ", "1"));
        assertEquals("1", Strings.join("", "", ", ", "1"));
        assertEquals("", Strings.join("", "", ", "));
    }

    @Test
    public void toInt() {
        assertEquals(1, Strings.toInt("1", 0));
        assertEquals(0, Strings.toInt("", 0));
    }

    @Test
    public void toLong() {
        assertEquals(1, Strings.toLong("1", 0));
        assertEquals(0, Strings.toLong("", 0));
    }

    @Test
    public void toFloat() {
        assertEquals(1, Strings.toFloat("1", 0), 0.0001);
        assertEquals(0, Strings.toFloat("", 0), 0.0001);
    }

    @Test
    public void toDouble() {
        assertEquals(1, Strings.toDouble("1", 0), 0.0001);
        assertEquals(0, Strings.toDouble("", 0), 0.0001);
    }

    @Test
    public void subStringBefore() {
        assertEquals("ab", Strings.subStringBefore("abcdc", "c"));
        assertEquals("ab", Strings.subStringBefore("ab", "c"));
        assertEquals("", Strings.subStringBefore("", "c"));
    }

    @Test
    public void subStringBeforeLast() {
        assertEquals("abcd", Strings.subStringBeforeLast("abcdc", "c"));
        assertEquals("ab", Strings.subStringBeforeLast("ab", "c"));
        assertEquals("", Strings.subStringBeforeLast("", "c"));
    }

    @Test
    public void subStringAfter() {
        assertEquals("dc", Strings.subStringAfter("abcdc", "c"));
        assertEquals("ab", Strings.subStringAfter("ab", "c"));
        assertEquals("", Strings.subStringAfter("", "c"));
    }

    @Test
    public void subStringAfterLast() {
        assertEquals("", Strings.subStringAfterLast("abcdc", "c"));
        assertEquals("ab", Strings.subStringAfter("ab", "c"));
        assertEquals("", Strings.subStringAfter("", "c"));
    }

    @Test
    public void countOf() {
        assertEquals(2, Strings.countOf("abcdc", "c"));
        assertEquals(2, Strings.countOf("abcdc", "c", true));
        assertEquals(1, Strings.countOf("ababa", "aba"));
        assertEquals(2, Strings.countOf("ababa", "aba", true));
        assertEquals(3, Strings.countOf("aaa", "a"));
        assertEquals(3, Strings.countOf("aaa", "a", true));
    }

    @Test
    public void removeSuffix() {
        assertEquals("test123", Strings.removeSuffix("test123tet", "tet"));
        assertEquals("test123tet", Strings.removeSuffix("test123tet", ""));
        assertEquals("test123tet", Strings.removeSuffix("test123tet", "123"));
        assertEquals("test123tet", Strings.removeSuffix("test123tet", "x"));
    }

    @Test
    public void removePrefix() {
        assertEquals("st123", Strings.removePrefix("test123", "te"));
        assertEquals("test123", Strings.removePrefix("test123", "tet"));
        assertEquals("test123", Strings.removePrefix("test123", "123"));
        assertEquals("test123", Strings.removePrefix("test123", "x"));
        assertEquals("test123", Strings.removePrefix("test123", ""));
    }

    @Test
    public void removeSuffix2() {
        assertEquals("test123", Strings.removeSuffix("test123", 0));
        assertEquals("test1", Strings.removeSuffix("test123", 2));
        assertEquals("", Strings.removeSuffix("test123", 7));
        assertEquals("", Strings.removeSuffix("test123", 10));
    }

    @Test
    public void removePrefix2() {
        assertEquals("test123", Strings.removePrefix("test123", 0));
        assertEquals("st123", Strings.removePrefix("test123", 2));
        assertEquals("", Strings.removePrefix("test123", 7));
        assertEquals("", Strings.removePrefix("test123", 10));
    }

    @Test
    void removePrefixAndSuffix() {
        assertEquals("test123", Strings.removePrefixAndSuffix("test123", "", ""));
        assertEquals("test123", Strings.removePrefixAndSuffix("test123", "xx", "xxx"));
        assertEquals("test", Strings.removePrefixAndSuffix("test123", "", "123"));
        assertEquals("123", Strings.removePrefixAndSuffix("test123", "test", ""));
        assertEquals("test123", Strings.removePrefixAndSuffix("test123", "123", ""));
        assertEquals("test123", Strings.removePrefixAndSuffix("test123", "", "tes"));
        assertEquals("", Strings.removePrefixAndSuffix("test123", "test", "123"));
        assertEquals("t", Strings.removePrefixAndSuffix("test123", "tes", "123"));
        assertEquals("", Strings.removePrefixAndSuffix("test123", "test1", "123"));
    }

    @Test
    public void capitalize() {
        assertEquals("", Strings.capitalize(""));
        assertEquals("1", Strings.capitalize("1"));
        assertEquals("爱1", Strings.capitalize("爱1"));
        assertEquals("Char", Strings.capitalize("Char"));
        assertEquals("C", Strings.capitalize("c"));
        assertEquals("Char", Strings.capitalize("char"));
    }

    @Test
    public void deCapitalize() {
        assertEquals("", Strings.deCapitalize(""));
        assertEquals("1", Strings.deCapitalize("1"));
        assertEquals("爱1", Strings.deCapitalize("爱1"));
        assertEquals("char", Strings.deCapitalize("Char"));
        assertEquals("c", Strings.deCapitalize("C"));
        assertEquals("char", Strings.deCapitalize("char"));
    }

    @Test
    void padLeft() {
        assertEquals("", Strings.padLeft("", 0, '1'));
        assertEquals("test", Strings.padLeft("test", 2, '1'));
        assertEquals("test", Strings.padLeft("test", 4, '1'));
        assertEquals("11test", Strings.padLeft("test", 6, '1'));
    }

    @Test
    void padRight() {
        assertEquals("", Strings.padRight("", 0, '1'));
        assertEquals("test", Strings.padRight("test", 2, '1'));
        assertEquals("test", Strings.padRight("test", 4, '1'));
        assertEquals("test11", Strings.padRight("test", 6, '1'));
    }

    @Test
    void padToCenter() {
        assertEquals("", Strings.padToCenter("", 0, '1'));
        assertEquals("test", Strings.padToCenter("test", 2, '1'));
        assertEquals("test", Strings.padToCenter("test", 4, '1'));
        assertEquals("test1", Strings.padToCenter("test", 5, '1'));
        assertEquals("1test1", Strings.padToCenter("test", 6, '1'));
    }

    @Test
    void trimSuffix() {
    }

    @Test
    void trimPrefix() {
    }

    @Test
    void joinLines() {
        assertEquals("", Strings.joinLines(Lists.of()));
        assertEquals("\n", Strings.joinLines(Lists.of("")));
        assertEquals("1\n\n", Strings.joinLines(Lists.of("1", "")));
    }
}