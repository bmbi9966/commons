package net.dongliu.commons.io;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.requireNonNull;

/**
 * Utils for InputStreams.
 */
public class InputStreams {

    private static final int BUFFER_SIZE = 16 * 1024;

    /**
     * Copy all data in InputStream to OutputStream.
     * Both in and out are left unclosed when copy finished, or Exception occurred.
     * @deprecated using {@link InputStream#transferTo(OutputStream)}
     */
    @Deprecated
    public static void transferTo(InputStream in, OutputStream out) throws IOException {
        in.transferTo(out);
    }

    /**
     * Read all data in InputStream. The Stream is left unclosed when read finished, or Exception occurred.
     @deprecated using {@link InputStream#readAllBytes()}
     */
    @Deprecated
    public static byte[] readAll(InputStream in) throws IOException {
        return in.readAllBytes();
    }

    /**
     * Read exact data with size, into data. The InputStream is not close after read or Exception occurred.
     *
     * @return The real data size read. If InputStream does not has enough data for read, will return a size less than desired.
     */
    public static int readExact(InputStream in, byte[] data, int offset, int size) throws IOException {
        Objects.checkFromIndexSize(offset, size, data.length);
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
     * Skip or read all data till EOF from InputStream. The InputStream is left unclosed.
     *
     * @return the total data size read
     */
    public static long discardAll(InputStream in) throws IOException {
        long total = 0;

        long skip;
        while ((skip = in.skip(BUFFER_SIZE)) > 0) {
            total += skip;
        }

        // read one byte to see if skip to end
        int r = in.read();
        if (r == -1) {
            return total;
        }

        total += 1;
        byte[] buffer = new byte[BUFFER_SIZE];
        int read;
        while ((read = in.read(buffer)) >= 0) {
            total += read;
        }
        return total;
    }


    /**
     * Return a empty input stream with no data.
     */
    public static InputStream empty() {
        return EmptyInputStream.instance;
    }

    /**
     * Return a new InputStream that concat multi sub InputStreams.
     * The close() method of returned InputStream will close all sub InputStreams.
     */
    public static InputStream concat(List<InputStream> inputs) {
        return new ConcatenatedInputStream(List.copyOf(inputs));
    }

    /**
     * Return a new InputStream that concat multi sub InputStreams.
     * The close() method of returned InputStream will close all sub InputStreams.
     */
    public static InputStream concat(InputStream... inputs) {
        return new ConcatenatedInputStream(List.of(inputs));
    }

    /**
     * Return a new InputStream that concat additional prepended data and InputStream.
     * The close() method of returned InputStream will close the passed in InputStream.
     */
    public static InputStream concat(byte[] data, InputStream in) {
        requireNonNull(data);
        return concat(new ByteArrayInputStream(data), in);
    }
}
