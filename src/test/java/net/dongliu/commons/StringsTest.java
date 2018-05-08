package net.dongliu.commons;

import net.dongliu.commons.collection.Lists;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

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
    public void trimSuffix() {
        assertEquals("test123", Strings.trimSuffix("test123tet", "tet"));
        assertEquals("test123tet", Strings.trimSuffix("test123tet", ""));
        assertEquals("test123tet", Strings.trimSuffix("test123tet", "x"));
    }

    @Test
    public void trimPrefix() {
        assertEquals("st123", Strings.trimPrefix("test123", "te"));
        assertEquals("test123", Strings.trimPrefix("test123", "tet"));
        assertEquals("test123", Strings.trimPrefix("test123", "x"));
        assertEquals("test123", Strings.trimPrefix("test123", ""));
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
}