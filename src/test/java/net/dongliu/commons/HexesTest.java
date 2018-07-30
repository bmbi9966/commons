package net.dongliu.commons;

import net.dongliu.commons.exception.HexDecodeException;
import org.junit.jupiter.api.Test;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.*;

class HexesTest {

    @Test
    void encode() {
        assertEquals("74657374E6B58BE8AF95", Hexes.encoder().encode("test测试".getBytes(UTF_8)));
        assertEquals("74657374e6b58be8af95", Hexes.encoder(false).encode("test测试".getBytes(UTF_8)));
    }

    @Test
    void decode() {
        assertArrayEquals("test测试".getBytes(UTF_8), Hexes.decoder().decode("74657374E6B58BE8AF95"));
        assertThrows(HexDecodeException.class, () -> Hexes.decoder().decode("74657374E6B58BE8AF9"));
        assertThrows(HexDecodeException.class, () -> Hexes.decoder().decode("74657374E6B58BE8AF9Y"));
    }
}