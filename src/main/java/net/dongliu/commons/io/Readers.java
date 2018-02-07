package net.dongliu.commons.io;

import java.io.*;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * Util Methods for Reader
 */
public class Readers {

    private static final int BUFFER_SIZE = 4 * 1024;

    /**
     * Copy all data in Reader to Writer.
     * Both Reader and Writer are leaved unclosed when copy finished, or Exception occurred.
     *
     * @throws IOException
     */
    public static void transferTo(Reader reader, Writer writer) throws IOException {
        char[] buffer = new char[BUFFER_SIZE];
        int count;
        while ((count = reader.read(buffer)) >= 0) {
            writer.write(buffer, 0, count);
        }
    }

    /**
     * Copy all data in Reader to Appendable.
     * Reader is leaved unclosed when copy finished, or Exception occurred.
     *
     * @throws IOException
     */
    public static void transferTo(Reader reader, Appendable appendable) throws IOException {
        char[] buffer = new char[BUFFER_SIZE];
        CharBuffer charBuffer = CharBuffer.wrap(buffer);
        int count;
        while ((count = reader.read(buffer)) >= 0) {
            charBuffer.position(0).limit(count);
            appendable.append(charBuffer);
        }
    }

    /**
     * Read all data in Reader. The Reader is leaved unclosed when read finished, or Exception occurred.
     *
     * @return String read from the reader
     * @throws IOException
     */
    public static String readAll(Reader reader) throws IOException {
        try (StringWriter writer = new StringWriter()) {
            transferTo(reader, writer);
            return writer.toString();
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
    public static List<String> toLines(Reader reader) throws IOException {
        BufferedReader br;
        if (reader instanceof BufferedReader) {
            br = (BufferedReader) reader;
        } else {
            br = new BufferedReader(reader);
        }
        List<String> list = new ArrayList<>();
        String line;
        while ((line = br.readLine()) != null) {
            list.add(line);
        }
        return list;
    }
}
