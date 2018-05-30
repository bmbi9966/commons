package net.dongliu.commons.io;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * More utils for file
 */
public class Files2 {

    /**
     * Read all file data, to string.
     *
     * @param path    the file path
     * @param charset the charset of file
     * @return the file data as string
     */
    public static String readAllString(Path path, Charset charset) throws IOException {
        return Readers.readAll(Files.newBufferedReader(path, charset));
    }

    /**
     * Read all file data, to string, with utf-8 encoding.
     *
     * @param path the file path
     * @return the file data as string
     */
    public static String readdAllStringUTF8(Path path) throws IOException {
        return readAllString(path, UTF_8);
    }
}
