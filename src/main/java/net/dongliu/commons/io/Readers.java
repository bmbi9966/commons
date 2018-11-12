package net.dongliu.commons.io;

import java.io.*;
import java.nio.Buffer;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Util Methods for Reader
 */
public class Readers {

    private static final int BUFFER_SIZE = 8 * 1024;

    /**
     * Copy all data in Reader to Writer.
     * Both Reader and Writer are leaved unclosed when copy finished, or Exception occurred.
     */
    public static void transferTo(Reader reader, Writer writer) {
        char[] buffer = new char[BUFFER_SIZE];
        int count;
        try {
            while ((count = reader.read(buffer)) >= 0) {
                writer.write(buffer, 0, count);
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    /**
     * Copy all data in Reader to Appendable.
     * Reader is leaved unclosed when copy finished, or Exception occurred.
     */
    public static void transferTo(Reader reader, Appendable appendable) {
        CharBuffer charBuffer = CharBuffer.allocate(BUFFER_SIZE);
        try {
            while (reader.read(charBuffer) >= 0) {
                ((Buffer) charBuffer).flip();
                appendable.append(charBuffer);
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }


    /**
     * Read all data in Reader. The Reader is leaved unclosed when read finished, or Exception occurred.
     *
     * @return String read from the reader
     */
    public static String readAll(Reader reader) {
        try (StringWriter writer = new StringWriter()) {
            transferTo(reader, writer);
            return writer.toString();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    /**
     * Wrap Reader as a closable line stream.
     * You need to make sure the returned Stream is closed, close the stream will also close the reader.
     *
     * @return the line stream
     */
    public static Stream<String> lines(Reader reader) {
        BufferedReader br;
        if (reader instanceof BufferedReader) {
            br = (BufferedReader) reader;
        } else {
            br = new BufferedReader(reader);
        }
        return br.lines();
    }


    /**
     * Read all from Reader to lines.
     * The Reader is leaved unclosed when copy finished, or Exception occurred.
     */
    public static List<String> toLines(Reader reader) {
        BufferedReader br;
        if (reader instanceof BufferedReader) {
            br = (BufferedReader) reader;
        } else {
            br = new BufferedReader(reader);
        }
        List<String> list = new ArrayList<>();
        String line;
        try {
            while ((line = br.readLine()) != null) {
                list.add(line);
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        return list;
    }

    /**
     * Read and discard all data in reader till EOF. The Reader is left unclosed.
     *
     * @return the char count read from reader.
     */
    public static long discardAll(Reader reader) {
        char[] buffer = new char[BUFFER_SIZE];
        long total = 0;
        int count;
        try {
            while ((count = reader.read(buffer)) >= 0) {
                total += count;
            }
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
        return total;
    }
}
