package net.dongliu.commons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class BytesTest {

    @Test
    void concat() {
        assertArrayEquals(Bytes.array((byte) 1, (byte) 2, (byte) 3, (byte) 1, (byte) 2, (byte) 3),
                Bytes.concat(Bytes.array((byte) 1, (byte) 2, (byte) 3), Bytes.array((byte) 1, (byte) 2, (byte) 3)));

    }
}