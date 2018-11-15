package net.dongliu.commons.io;

import net.dongliu.commons.Strings;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.StringReader;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ReadersTest {

    private static String str = "<modelVersion>4.0.0</modelVersion>\n" +
            "    <groupId>net.dongliu</groupId>\n" +
            "    <artifactId>commons</artifactId>";

    @Test
    public void transferTo() throws IOException {
        StringBuilder sb = new StringBuilder();
        Readers.transferTo(new StringReader(str), sb);
        assertEquals(str, sb.toString());

        String s = Strings.repeat(str, 100);
        sb = new StringBuilder();
        Readers.transferTo(new StringReader(s), sb);
        assertEquals(s, sb.toString());
    }

    @Test
    public void readAll() throws IOException {
        String s = Readers.readAll(new StringReader(str));
        assertEquals(str, s);
    }

    @Test
    public void toLines() throws IOException {
        List<String> lines = Readers.toLines(new StringReader(str));
        assertEquals(Arrays.asList(str.split("\n")), lines);
    }
}