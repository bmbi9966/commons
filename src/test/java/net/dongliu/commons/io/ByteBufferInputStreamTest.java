package net.dongliu.commons.io;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class ByteBufferInputStreamTest {
    private Random random = new Random();

    @Test
    public void read() throws IOException {
        byte[] bytes = new byte[4096];
        random.nextBytes(bytes);
        ByteBufferInputStream in = new ByteBufferInputStream(ByteBuffer.wrap(bytes));
        byte[] newBytes = InputStreams.readAll(in);
        assertArrayEquals(bytes, newBytes);
    }

    @Test
    public void skip() {
        byte[] bytes = new byte[4096];
        random.nextBytes(bytes);
        ByteBufferInputStream in = new ByteBufferInputStream(ByteBuffer.wrap(bytes));
        assertEquals(4094, in.skip(4094));
    }

    @Test
    public void available() {
        byte[] bytes = new byte[4096];
        random.nextBytes(bytes);
        ByteBufferInputStream in = new ByteBufferInputStream(ByteBuffer.wrap(bytes));
        assertEquals(4096, in.available());
    }

    @Test
    public void mark() throws IOException {
        byte[] bytes = new byte[4096];
        random.nextBytes(bytes);
        ByteBufferInputStream in = new ByteBufferInputStream(ByteBuffer.wrap(bytes));
        assertTrue(in.markSupported());
        in.mark(1000000);
        byte[] first = InputStreams.readExact(in, 1024);
        assertEquals(1024, first.length);
        assertEquals(3072, in.available());
        in.reset();
        assertEquals(4096, in.available());
        byte[] newBytes = InputStreams.readAll(in);
        assertArrayEquals(bytes, newBytes);
    }
}