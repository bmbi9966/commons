package net.dongliu.commons.io;

import org.junit.jupiter.api.Test;

import java.io.IOException;

import static java.nio.charset.StandardCharsets.US_ASCII;
import static org.junit.jupiter.api.Assertions.*;

class ByteBufferOutputStreamTest {

    @Test
    void test() throws IOException {
        var output = new ByteBufferOutputStream(false);
        output.write(1);
        output.write("test".getBytes(US_ASCII));
        output.close();
        var buffer = output.getBuffer();
        assertEquals(1, buffer.get());
        var bytes = new byte[4];
        buffer.get(bytes);
        assertArrayEquals("test".getBytes(US_ASCII), bytes);
        assertThrows(IOException.class, () -> output.write(1));
    }


    @Test
    void testLarge() throws IOException {
        var output = new ByteBufferOutputStream(false);
        var bytes = new byte[1024];
        for (int i = 0; i < 100; i++) {
            output.write(bytes);
        }
        output.close();
        var buffer = output.getBuffer();
        assertEquals(1024 * 100, buffer.remaining());
    }
}