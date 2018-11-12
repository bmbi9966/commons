package net.dongliu.commons.io;


import org.junit.jupiter.api.Test;

import java.nio.Buffer;
import java.nio.ByteBuffer;

import static java.nio.ByteOrder.LITTLE_ENDIAN;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BuffersTest {

    @Test
    public void advance() {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        ((Buffer) buffer).position(200);
        Buffers.advance(buffer, 100);
        assertEquals(300, buffer.position());
        Buffers.advance(buffer, -200);
        assertEquals(100, buffer.position());
        Buffers.advance(buffer, -100);
        assertEquals(0, buffer.position());
    }

    @Test
    public void slice() {
        ByteBuffer buffer = ByteBuffer.allocate(1024).order(LITTLE_ENDIAN);
        ByteBuffer slice = Buffers.slice(buffer);
        assertEquals(LITTLE_ENDIAN, slice.order());
    }

    @Test
    public void getUByte() {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.put((byte) 1);
        buffer.put((byte) -1);
        ((Buffer) buffer).flip();
        assertEquals(1, Buffers.getUByte(buffer));
        assertEquals(255, Buffers.getUByte(buffer));
    }

    @Test
    public void getUShort() {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.putShort((short) 1);
        buffer.putShort((short) -1);
        ((Buffer) buffer).flip();
        assertEquals(1, Buffers.getUShort(buffer));
        assertEquals(65535, Buffers.getUShort(buffer));
    }

    @Test
    public void getUInt() {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.putInt(1);
        buffer.putInt(-1);
        ((Buffer) buffer).flip();
        assertEquals(1L, Buffers.getUInt(buffer));
        assertEquals(0xffffffffL, Buffers.getUInt(buffer));
    }

    @Test
    public void ensureGetULong() {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.putLong(1);
        buffer.putLong(-1);
        ((Buffer) buffer).flip();
        assertEquals(1L, Buffers.ensureGetULong(buffer));
        assertThrows(ArithmeticException.class, () -> Buffers.ensureGetULong(buffer));
    }

    @Test
    void ensureGetUInt() {
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        buffer.putInt(1);
        buffer.putInt(-1);
        ((Buffer) buffer).flip();
        assertEquals(1, Buffers.ensureGetUInt(buffer));
        assertThrows(ArithmeticException.class, () -> Buffers.ensureGetUInt(buffer));
    }
}