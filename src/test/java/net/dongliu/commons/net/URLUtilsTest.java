package net.dongliu.commons.net;

import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class URLUtilsTest {

    @Test
    void resolve() throws MalformedURLException {
        assertEquals("http://www.test.com", URLUtils.resolve("http://www.test.com", ""));
        assertEquals("http://www.test.com/", URLUtils.resolve("http://www.test.com/", ""));

        assertEquals("https://c.com/a.html", URLUtils.resolve("http://www.test.com", "https://c.com/a.html"));

        assertEquals("http://c.com/a.html", URLUtils.resolve("http://www.test.com", "//c.com/a.html"));
        assertEquals("http://c.com/a.html", URLUtils.resolve("http://www.test.com/c", "//c.com/a.html"));
        assertEquals("https://c.com/a.html", URLUtils.resolve("https://www.test.com/c", "//c.com/a.html"));

        assertEquals("http://www.test.com/a.html", URLUtils.resolve("http://www.test.com", "/a.html"));
        assertEquals("http://www.test.com/a.html", URLUtils.resolve("http://www.test.com/", "/a.html"));
        assertEquals("http://www.test.com/a.html", URLUtils.resolve("http://www.test.com/b/", "/a.html"));

        assertEquals("http://www.test.com/#test", URLUtils.resolve("http://www.test.com", "#test"));
        assertEquals("http://www.test.com/#test", URLUtils.resolve("http://www.test.com/", "#test"));
        assertEquals("http://www.test.com/a#test", URLUtils.resolve("http://www.test.com/a", "#test"));
        assertEquals("http://www.test.com/a?b=c#test", URLUtils.resolve("http://www.test.com/a?b=c", "#test"));

        assertEquals("http://www.test.com/a.html", URLUtils.resolve("http://www.test.com", "a.html"));
        assertEquals("http://www.test.com/a.html", URLUtils.resolve("http://www.test.com/", "a.html"));
        assertEquals("http://www.test.com/a.html", URLUtils.resolve("http://www.test.com/b", "a.html"));
        assertEquals("http://www.test.com/b/a.html", URLUtils.resolve("http://www.test.com/b/", "a.html"));

        assertEquals("http://www.test.com/b/a.html", URLUtils.resolve("http://www.test.com/b/", "./a.html"));
        assertEquals("http://www.test.com/a.html", URLUtils.resolve("http://www.test.com/b/", "./../a.html"));
        assertEquals("http://www.test.com/a.html", URLUtils.resolve("http://www.test.com/b/", "../a.html"));
        assertEquals("http://www.test.com/a.html", URLUtils.resolve("http://www.test.com/b/c/d", "./../../a.html"));
        assertThrows(MalformedURLException.class, () -> URLUtils.resolve("http://www.test.com/b", "../a.html"));
    }

    @Test
    void encodeParams() {
        assertEquals("", URLUtils.encodeParams(List.of()));
        assertEquals("test=1", URLUtils.encodeParams(List.of(Map.entry("test", "1"))));
        assertEquals("test=1&x=%E6%B5%8B%E8%AF%95", URLUtils.encodeParams(List.of(Map.entry("test", "1"), Map.entry("x", "测试"))));
        assertEquals("test=%B2%E2%CA%D4", URLUtils.encodeParams(List.of(Map.entry("test", "测试")), Charset.forName("GB18030")));
    }

    @Test
    void parseParams() {
        assertEquals(List.of(), URLUtils.parseParams(""));
        assertEquals(List.of(Map.entry("test", "1")), URLUtils.parseParams("test=1"));
        assertEquals(List.of(Map.entry("test", "1"), Map.entry("", "2")), URLUtils.parseParams("test=1&2"));
        assertEquals(List.of(Map.entry("test", "1"), Map.entry("x", "")), URLUtils.parseParams("test=1&x="));
        assertEquals(List.of(Map.entry("test", "1"), Map.entry("x", "2=3")), URLUtils.parseParams("test=1&x=2=3"));
        assertEquals(List.of(Map.entry("test", "1"), Map.entry("x", "测试")), URLUtils.parseParams("test=1&x=%E6%B5%8B%E8%AF%95"));
        assertEquals(List.of(Map.entry("test", "测试")), URLUtils.parseParams("test=%B2%E2%CA%D4", Charset.forName("GB18030")));
    }
}