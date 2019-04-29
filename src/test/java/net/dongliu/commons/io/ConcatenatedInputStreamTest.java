package net.dongliu.commons.io;

import net.dongliu.commons.StringRandom;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ConcatenatedInputStreamTest {

    @Test
    void test() throws IOException {
        var sr = new StringRandom();
        var s1 = sr.asciiString(1024);
        var s2 = sr.alphanumericString(4096);
        var input = new ConcatenatedInputStream(List.of(
                new ByteArrayInputStream(s1.getBytes()),
                new ByteArrayInputStream(s2.getBytes())
        ));
        var result = new String(input.readAllBytes());
        assertEquals(s1 + s2, result);
        input.close();

        input = new ConcatenatedInputStream(List.of(
                new ByteArrayInputStream(s1.getBytes()),
                new ByteArrayInputStream(s2.getBytes())
        ));
        assertEquals(1024 + 4096, input.skip(1024 + 4096));
        assertEquals(-1, input.read());
        input.close();
    }
}