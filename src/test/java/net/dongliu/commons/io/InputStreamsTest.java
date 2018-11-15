package net.dongliu.commons.io;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class InputStreamsTest {

    private static byte[] data = {1, 2, 5, 7, 8, 10, 22};
    private static byte[] dataLarge;

    @BeforeAll
    public static void init() {
        dataLarge = new byte[10000];
        Random random = new Random();
        random.nextBytes(dataLarge);
    }

    @Test
    public void transferTo() throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        InputStreams.transferTo(new ByteArrayInputStream(data), bos);
        assertArrayEquals(data, bos.toByteArray());
    }

    @Test
    public void readAll() throws IOException {
        InputStream in = new ByteArrayInputStream(data);
        byte[] bytes = InputStreams.readAll(in);
        assertArrayEquals(data, bytes);

        assertArrayEquals(dataLarge, InputStreams.readAll(new ByteArrayInputStream(dataLarge)));
    }

    @Test
    public void readExact() throws IOException {
        byte[] bytes = InputStreams.readExact(new ByteArrayInputStream(dataLarge), 1024);
        assertArrayEquals(Arrays.copyOf(dataLarge, 1024), bytes);

        byte[] buffer = new byte[10];
        assertThrows(IndexOutOfBoundsException.class,
                () -> InputStreams.readExact(new ByteArrayInputStream(dataLarge), buffer, 0, 1024));
    }

    @Test
    public void discardAll() {
    }

    @Test
    public void empty() throws IOException {
        InputStream in = InputStreams.empty();
        assertEquals(-1, in.read());
        byte[] buffer = new byte[1024];
        assertEquals(-1, in.read(buffer));
        assertEquals(0, InputStreams.discardAll(in));
    }
}