package net.dongliu.commons.io;

import net.dongliu.commons.Preconditions;
import org.jetbrains.annotations.NotNull;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

/**
 * Utils for InputStreams.
 */
public class InputStreams {

    private static final int BUFFER_SIZE = 8 * 1024;

    /**
     * Copy all data in InputStream to OutputStream.
     * Both in and out are leaved unclosed when copy finished, or Exception occurred.
     *
     * @throws IOException
     */
    public static void transferTo(InputStream in, OutputStream out) throws IOException {
        byte[] buffer = new byte[BUFFER_SIZE];
        int count;
        while ((count = in.read(buffer)) >= 0) {
            out.write(buffer, 0, count);
        }
    }

    /**
     * Read all data in InputStream. The Stream is leaved unclosed when read finished, or Exception occurred.
     *
     * @throws IOException
     */
    public static byte[] readAll(InputStream in) throws IOException {
        int initialSize = Math.max(32, in.available());
        try (ByteArrayOutputStream bos = new ByteArrayOutputStream(initialSize)) {
            transferTo(in, bos);
            return bos.toByteArray();
        }
    }

    /**
     * Read exact data with size, into data. The InputStream is not close after read or Exception occurred.
     *
     * @return The real data size read. If InputStream does not has enough data for read, will return a size less than desired.
     * @throws IOException
     */
    public static int readExact(InputStream in, byte[] data, int offset, int size) throws IOException {
        Preconditions.checkSubRange(data.length, offset, size);
        int read = 0;
        int count;
        while (read < size && (count = in.read(data, offset + read, size - read)) >= 0) {
            read += count;
        }
        return read;
    }

    /**
     * Read exact data with size. The InputStream is not close after read or Exception occurred.
     *
     * @return The data read. If InputStream does not has enough data for read, will return a byte array which len less than desired size.
     * @throws IOException
     */
    public static byte[] readExact(InputStream in, int size) throws IOException {
        if (size < 0) {
            throw new IllegalArgumentException("size less then 0");
        }
        byte[] buffer = new byte[size];
        int read = readExact(in, buffer, 0, size);
        if (read < size) {
            return Arrays.copyOf(buffer, read);
        }
        return buffer;
    }

    /**
     * Read all data till EOF from InputStream. The InputStream is leaved unclosed.
     *
     * @return the total data size read
     * @throws IOException
     */
    public static long consumeAll(InputStream in) throws IOException {
        long total = 0;
        byte[] buffer = new byte[BUFFER_SIZE];
        int read;
        while ((read = in.read(buffer)) >= 0) {
            total += read;
        }
        return total;
    }

    /**
     * Use skip to discard all data from InputStream.
     * If still has data after InputStream.skip returns 0, use read method to consume data.
     * The InputStream is leaved unclosed.
     *
     * @return the total data size discard
     * @throws IOException
     */
    public static long discardAll(InputStream in) throws IOException {
        long total = 0;
        long read;
        while ((read = in.skip(BUFFER_SIZE)) > 0) {
            total += read;
        }
        total += consumeAll(in);
        return total;
    }

    /**
     * Return a empty input stream with no data.
     */
    public static InputStream empty() {
        return new InputStream() {
            @Override
            public int read(@NotNull byte[] b) {
                return -1;
            }

            @Override
            public int read(@NotNull byte[] b, int off, int len) {
                return -1;
            }

            @Override
            public long skip(long n) {
                return 0;
            }

            @Override
            public int available() {
                return 0;
            }

            @Override
            public int read() {
                return -1;
            }
        };
    }
}
