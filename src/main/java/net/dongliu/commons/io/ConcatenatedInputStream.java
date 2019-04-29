package net.dongliu.commons.io;

import net.dongliu.commons.Throwables;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Objects;

/**
 * A InputStream that concat multi sub InputStreams.
 * The {@link #close()} method close all sub InputStreams.
 */
class ConcatenatedInputStream extends InputStream {

    private final List<InputStream> inputs;
    private int index = 0;

    ConcatenatedInputStream(List<InputStream> inputs) {
        this.inputs = List.copyOf(inputs);
    }

    @Override
    public synchronized int read(byte[] b) throws IOException {
        return read(b, 0, b.length);
    }

    @Override
    public synchronized int read(byte[] b, int off, int len) throws IOException {
        Objects.checkFromIndexSize(off, len, b.length);
        int totalRead = 0;
        while (true) {
            int read = current().read(b, off + totalRead, len - totalRead);
            if (read == -1) {
                if (!next()) {
                    if (totalRead == 0) {
                        totalRead = -1;
                    }
                    break;
                }
            } else if (read == 0) {
                break;
            } else {
                totalRead += read;
                if (totalRead == len) {
                    break;
                }
            }
        }
        return totalRead;
    }

    @Override
    public synchronized long skip(long n) throws IOException {
        return super.skip(n);
    }

    @Override
    public synchronized int available() throws IOException {
        return current().available();
    }

    @Override
    public synchronized void close() throws IOException {
        @Nullable Throwable t = null;
        for (var input : inputs) {
            try {
                input.close();
            } catch (Throwable e) {
                if (t == null) {
                    t = e;
                } else {
                    t.addSuppressed(e);
                }
            }
        }
        if (t != null) {
            throw Throwables.sneakyThrow(t);
        }
    }

    @Override
    public synchronized long transferTo(OutputStream out) throws IOException {
        long total = 0;
        do {
            total += current().transferTo(out);
        } while (next());
        return total;
    }

    @Override
    public synchronized int read() throws IOException {
        int value;
        do {
            value = current().read();
        } while (value == -1 && next());
        return value;
    }

    private InputStream current() {
        return inputs.get(index);
    }

    private boolean next() {
        if (index >= inputs.size() - 1) {
            return false;
        }
        index++;
        return true;
    }
}
