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
}