package net.dongliu.commons.io;


import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.util.Objects;

/**
 * A OutputStream that write date to ByteBuffer.
 * After closed, the ByteBuffer contains the data can be get by {@link #getBuffer}
 */
public class ByteBufferOutputStream extends OutputStream {
    private final boolean direct;
    private boolean closed;
    private ByteBuffer buffer;

    /**
     * Create a new ByteBufferOutputStream.
     *
     * @param direct if use direct buffer
     */
    public ByteBufferOutputStream(boolean direct) {
        this.direct = direct;
        this.closed = false;
        this.buffer = allocate(256);
    }

    /**
     * Get the underlying ByteBuffer contains the result data ready for read.
     * This method should be called after {@link #close()} method
     *
     * @throws IllegalStateException if stream not closed.
     */
    public synchronized ByteBuffer getBuffer() {
        if (!closed) {
            throw new IllegalStateException("stream not closed yet");
        }
        return buffer;
    }

    @Override
    public synchronized void write(byte[] b) throws IOException {
        checkClosed();
        write(b, 0, b.length);
    }

    @Override
    public synchronized void write(byte[] b, int off, int len) throws IOException {
        Objects.checkFromIndexSize(off, len, b.length);
        checkClosed();
        if (buffer.remaining() < len) {
            expand(Math.addExact(buffer.position(), len));
        }
        buffer.put(b, off, len);
    }

    @Override
    public synchronized void flush() throws IOException {
        checkClosed();
    }

    @Override
    public synchronized void close() throws IOException {
        if (!closed) {
            closed = true;
            buffer.flip();
        }
    }

    @Override
    public synchronized void write(int b) throws IOException {
        checkClosed();
        if (buffer.remaining() < 1) {
            expand(Math.addExact(buffer.position(), 1));
        }
        buffer.put((byte) b);
    }

    private void checkClosed() throws IOException {
        if (closed) {
            throw new IOException("Stream closed.");
        }
    }

    private void expand(int size) {
        int newSize = buffer.capacity();
        while (newSize < size) {
            newSize = newSize << 1;
        }
        var newBuffer = allocate(newSize);
        buffer.flip();
        newBuffer.put(buffer);
        buffer = newBuffer;
    }

    private ByteBuffer allocate(int size) {
        return direct ? ByteBuffer.allocateDirect(size) : ByteBuffer.allocate(size);
    }
}
