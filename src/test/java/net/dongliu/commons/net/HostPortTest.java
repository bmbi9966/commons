package net.dongliu.commons.net;

import org.junit.jupiter.api.Test;

import java.util.OptionalInt;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class HostPortTest {

    @Test
    void of() {
        var hostPort = HostPort.of("123.10.10.1:20");
        assertEquals("123.10.10.1", hostPort.host());
        assertEquals(OptionalInt.of(20), hostPort.port());

        hostPort = HostPort.of("123.10.10.1");
        assertEquals("123.10.10.1", hostPort.host());
        assertEquals(OptionalInt.empty(), hostPort.port());

        assertThrows(IllegalArgumentException.class, () -> HostPort.of("test.com:"));
        assertThrows(IllegalArgumentException.class, () -> HostPort.of("test.com:abc"));
        assertThrows(IllegalArgumentException.class, () -> HostPort.of("test.com:abc:123"));
        assertThrows(IllegalArgumentException.class, () -> HostPort.of("test.com:789123"));
    }

    @Test
    void withHost() {
    }

    @Test
    void withPort() {
    }

    @Test
    void equals() {
    }
}