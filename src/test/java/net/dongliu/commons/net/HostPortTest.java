package net.dongliu.commons.net;

import org.junit.jupiter.api.Test;

import java.util.OptionalInt;

import static org.junit.jupiter.api.Assertions.*;

class HostPortTest {

    @Test
    void parse() {
        var hostPort = HostPort.parse("123.10.10.1:20");
        assertEquals("123.10.10.1", hostPort.host());
        assertEquals(OptionalInt.of(20), hostPort.port());

        hostPort = HostPort.parse("123.10.10.1");
        assertEquals("123.10.10.1", hostPort.host());
        assertEquals(OptionalInt.empty(), hostPort.port());

        assertThrows(IllegalArgumentException.class, () -> HostPort.parse("test.com:"));
        assertThrows(IllegalArgumentException.class, () -> HostPort.parse("test.com:abc"));
        assertThrows(IllegalArgumentException.class, () -> HostPort.parse("test.com:abc:123"));
        assertThrows(IllegalArgumentException.class, () -> HostPort.parse("test.com:789123"));
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

    @Test
    void hasPort() {
        var hostPort = new HostPort("local");
        assertFalse(hostPort.hasPort());

        hostPort = new HostPort("local", 8080);
        assertTrue(hostPort.hasPort());

    }


    @Test
    void ensurePort() {
        var hostPort = new HostPort("local");
        assertThrows(IllegalStateException.class, hostPort::ensurePort);

        hostPort = new HostPort("local", 8080);
        assertEquals(8080, hostPort.ensurePort());
    }
}