package net.dongliu.commons;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SplitterTest {

    @Test
    void splitAsStream() {
        var splitter = Splitter.of(",");
        assertEquals(List.of("1", "2"), splitter.split("1,2").toImmutableList());
    }

    @Test
    void of() {
        var splitter = Splitter.of(",");
        assertEquals(List.of(""), splitter.splitToList(""));
        assertEquals(List.of("1", "2"), splitter.splitToList("1,2"));
        assertEquals(List.of("", ""), splitter.splitToList(","));

        splitter = Splitter.of(",,");
        assertEquals(List.of("", ""), splitter.splitToList(",,"));
        assertEquals(List.of("", ","), splitter.splitToList(",,,"));
        assertEquals(List.of("", "", ""), splitter.splitToList(",,,,"));

        splitter = Splitter.of("");
        assertEquals(List.of(""), splitter.splitToList(""));
        assertEquals(List.of("1"), splitter.splitToList("1"));
        assertEquals(List.of("1", "2", "3"), splitter.splitToList("123"));
    }


    @Test
    void ofRegex() {
        var splitter = Splitter.ofRegex(Pattern.compile("\\d+"));
        assertEquals(List.of(""), splitter.splitToList(""));
        assertEquals(List.of("", "+", ""), splitter.splitToList("1+2"));
        assertEquals(List.of("", ""), splitter.splitToList("1"));
        assertEquals(List.of("", ""), splitter.splitToList("11"));

        splitter = Splitter.ofRegex(".*?");
        assertEquals(List.of(""), splitter.splitToList(""));
        assertEquals(List.of("1"), splitter.splitToList("1"));
        assertEquals(List.of("1", "2", "3"), splitter.splitToList("123"));

        splitter = Splitter.ofRegex("\\d+");
        assertEquals(List.of(""), splitter.splitToList(""));
        assertEquals(List.of("", ""), splitter.splitToList("1"));
        assertEquals(List.of("", "a"), splitter.splitToList("1a"));
        assertEquals(List.of("a"), splitter.splitToList("a"));
    }

    @Test
    void skipEmpty() {
        var splitter = Splitter.of(",").skipEmpty();
        assertEquals(List.of(), splitter.splitToList(""));
        assertEquals(List.of(), splitter.splitToList(","));
        assertEquals(List.of("1"), splitter.splitToList(",1"));

        splitter = Splitter.of("").skipEmpty();
        assertEquals(List.of(), splitter.splitToList(""));
        assertEquals(List.of("1"), splitter.splitToList("1"));
    }

    @Test
    void trimResults() {
        var splitter = Splitter.of(",").trimResults();
        assertEquals(List.of(""), splitter.splitToList(""));
        assertEquals(List.of(""), splitter.splitToList(" "));
        assertEquals(List.of("", "1"), splitter.splitToList(",1"));

        splitter = Splitter.of(",").trimResults().skipEmpty();
        assertEquals(List.of(), splitter.splitToList(""));
        assertEquals(List.of(), splitter.splitToList(" "));
        assertEquals(List.of("1"), splitter.splitToList(",1"));
    }

    @Test
    void prefixSuffix() {
        var splitter = Splitter.of(",").skipPrefix("{").skipSuffix("}");
        assertEquals(List.of(""), splitter.splitToList(""));
        assertEquals(List.of("1", "2"), splitter.splitToList("1,2"));
        assertEquals(List.of(""), splitter.splitToList("{}"));
        assertEquals(List.of("1", "2"), splitter.splitToList("{1,2}"));

        splitter = Splitter.of(",").skipPrefix("{").skipSuffix("}").skipEmpty();
        assertEquals(List.of(), splitter.splitToList("{}"));
        assertEquals(List.of(), splitter.splitToList("{,}"));
        assertEquals(List.of("1"), splitter.splitToList("{1,}"));
        assertEquals(List.of("1", "2"), splitter.splitToList("{1,2}"));
    }
}