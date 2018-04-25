package net.dongliu.commons.io;

import net.dongliu.commons.Preconditions;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

import static java.lang.Byte.toUnsignedInt;
import static java.util.Objects.requireNonNull;

/**
 * Wrap a byte buffer as input stream. The ByteBuffer's position will change after read operation.
 */
public class ByteBufferInputStream extends InputStream {
    private final ByteBuffer buffer;

    private int mark = -1;

    public ByteBufferInputStream(ByteBuffer buffer) {
        this.buffer = requireNonNull(buffer);
    }


    @Override
    public synchronized int read(@NotNull byte[] b) {
        requireNonNull(b);
        return read(b, 0, b.length);
    }

    @Override
    public synchronized int read(@NotNull byte[] b, int off, int len) {
        requireNonNull(b);
        Preconditions.checkSubRange(b.length, off, len);
        if (!buffer.hasRemaining()) {
            return -1;
        }
        int toRead = Math.min(buffer.remaining(), len);
        buffer.get(b, 0, toRead);
        return toRead;
    }

    @Override
    public synchronized long skip(long n) {
        if (n <= 0) {
            return 0;
        }
        int toSkip = (int) Math.min(buffer.remaining(), n);
        buffer.position(buffer.position() + toSkip);
        return toSkip;
    }

    @Override
    public synchronized int available() {
        return buffer.remaining();
    }

    @Override
    public void close() {
    }

    @Override
    public synchronized void mark(int readlimit) {
        this.mark = buffer.position();
    }

    @Override
    public synchronized void reset() throws IOException {
        if (this.mark == -1) {
            throw new IOException("stream not marked");
        }
        buffer.position(this.mark);
        this.mark = -1;
    }

    @Override
    public boolean markSupported() {
        return true;
    }

    @Override
    public synchronized int read() {
        if (buffer.hasRemaining()) {
            return toUnsignedInt(buffer.get());
        }
        return -1;
    }
}
