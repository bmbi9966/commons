package net.dongliu.commons.io;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.Charset;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Path;

import static java.nio.charset.StandardCharsets.UTF_8;

/**
 * More utils for file
 */
public class Files2 {

    /**
     * Copy source file to target file, make target dirs if need.
     * If target file already exits, override it.
     */
    public static void copyFile(Path source, Path target, CopyOption... copyOptions) {
        Path targetDir = target.getParent();
        if (!Files.exists(targetDir)) {
            if (!targetDir.toFile().mkdirs()) {
                throw new UncheckedIOException(new IOException("make target file dirs failed"));
            }
        }
        try {
            Files.copy(source, target, copyOptions);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    /**
     * Copy source file to target file, make target dirs if need.
     * If target file already exits, override it.
     */
    public static void moveFile(Path source, Path target, CopyOption... copyOptions) {
        Path targetDir = target.getParent();
        if (!Files.exists(targetDir)) {
            if (!targetDir.toFile().mkdirs()) {
                throw new UncheckedIOException(new IOException("make target file dirs failed"));
            }
        }
        try {
            Files.move(source, target, copyOptions);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    /**
     * Read all file data, to string.
     *
     * @param path    the file path
     * @param charset the charset of file
     * @return the file data as string
     */
    public static String readAllString(Path path, Charset charset) {
        try {
            return Readers.readAll(Files.newBufferedReader(path, charset));
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    /**
     * Read all file data, to string, with utf-8 encoding.
     *
     * @param path the file path
     * @return the file data as string
     */
    public static String readdAllString(Path path) {
        return readAllString(path, UTF_8);
    }
}
