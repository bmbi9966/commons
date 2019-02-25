package net.dongliu.commons.net;

import org.junit.jupiter.api.Test;

import java.net.MalformedURLException;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

class URLBuilderTest {

    @Test
    void test() throws MalformedURLException {
        var builder = URLBuilder.ofURL("http://www.test.com/a?123#test");
        assertEquals("https://www.test.com/a?123#test", builder.protocol("https").buildURLString());
        assertEquals("https://www.test.com/a?x=1#test", builder.protocol("https")
                .query(List.of(Map.entry("x", "1")))
                .buildURLString());
        assertEquals("https://test.com:8443/a/b/c?x=1#anchor", builder.protocol("https")
                .host("test.com")
                .port(8443)
                .path("/a/b/c")
                .query(List.of(Map.entry("x", "1")))
                .fragment("anchor")
                .buildURLString());
    }

}