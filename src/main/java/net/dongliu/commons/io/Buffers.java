package net.dongliu.commons.io;

import java.nio.Buffer;
import java.nio.BufferOverflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

/**
 * Utils for buffers
 */
public class Buffers {

    /**
     * Increase buffer position to new position, by step. if step is less than zero, the position is moved back.
     *
     * @param buffer the buffer
     * @param step   the step to increase the position
     * @throws BufferOverflowException if new position is not a valid position.
     */
    public static void advance(Buffer buffer, int step) {
        if (step < -buffer.position() || step > buffer.remaining()) {
            throw new BufferOverflowException();
        }
        buffer.position(buffer.position() + step);
    }


    // ---------- for ByteBuffer ---------------

    /**
     * This method return a subsequent buffer as {@link ByteBuffer#slice()},
     * with additional set the byte order of sliced new ByteBuffer to the same of original buffer.
     *
     * @param buffer the origin buffer
     * @return the new buffer hold the remain data of original buffer
     */
    public static ByteBuffer slice(ByteBuffer buffer) {
        ByteOrder order = buffer.order();
        return buffer.slice().order(order);
    }

    /**
     * Get a byte, as unsigned byte from buffer.
     *
     * @return a short value hold the unsigned byte.
     */
    public static short getUByte(ByteBuffer buffer) {
        return (short) (buffer.get() & 0xff);
    }

    /**
     * Get two bytes, as unsigned short value from buffer.
     *
     * @return a int value hold the unsigned short value.
     */
    public static int getUShort(ByteBuffer buffer) {
        return buffer.getShort() & 0xffff;
    }

    /**
     * Get four bytes, as unsigned int value from buffer.
     *
     * @return a long value hold the unsigned int value.
     */
    public static long getUInt(ByteBuffer buffer) {
        return ((long) buffer.getInt()) & 0xffffffffL;
    }

    /**
     * Get four bytes, as unsigned long value from buffer. If the value can not hold in unsigned int,
     * a exception thrown.
     *
     * @return a int value hold the unsigned int value.
     * @throws ArithmeticException if int can not hold this unsigned int value
     */
    public static int ensureGetUInt(ByteBuffer buffer) {
        int v = buffer.getInt();
        if (v < 0) {
            throw new ArithmeticException("overflow unsigned long");
        }
        return v;
    }


    /**
     * Get eight bytes, as unsigned long value from buffer. If the value can not hold in unsigned long,
     * a exception thrown.
     *
     * @return a long value hold the unsigned long value.
     * @throws ArithmeticException if long can not hold this unsigned long value
     */
    public static long ensureGetULong(ByteBuffer buffer) {
        long v = buffer.getLong();
        if (v < 0) {
            throw new ArithmeticException("overflow unsigned long");
        }
        return v;
    }
}
