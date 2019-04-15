package net.dongliu.commons.net;

import org.junit.jupiter.api.Test;

import java.util.OptionalInt;

import static org.junit.jupiter.api.Assertions.*;

class HostPortTest {

    void of() {
        var hostPort = HostPort.of("::FFFF:129.144.52.38");
        assertEquals(HostType.IPv6, hostPort.type());

        hostPort = HostPort.of("::1");
        assertEquals(HostType.IPv6, hostPort.type());

        hostPort = HostPort.of("127.0.0.1");
        assertEquals(HostType.IPv4, hostPort.type());

        hostPort = HostPort.of("1.test.com");
        assertEquals(HostType.domain, hostPort.type());
        hostPort = HostPort.of("localhost");
        assertEquals(HostType.domain, hostPort.type());

        assertThrows(IllegalArgumentException.class, () -> HostPort.of(""));
        assertThrows(IllegalArgumentException.class, () -> HostPort.of("-aaa.test"));
        assertThrows(IllegalArgumentException.class, () -> HostPort.of("111.100"));
    }

    @Test
    void parse() {
        var hostPort = HostPort.parse("123.10.10.1:20");
        assertEquals("123.10.10.1", hostPort.host());
        assertEquals(OptionalInt.of(20), hostPort.port());

        hostPort = HostPort.parse("123.10.10.1");
        assertEquals("123.10.10.1", hostPort.host());
        assertEquals(OptionalInt.empty(), hostPort.port());

        hostPort = HostPort.parse("::1");
        assertEquals("::1", hostPort.host());
        assertFalse(hostPort.hasPort());

        hostPort = HostPort.parse("[::1]");
        assertEquals("::1", hostPort.host());
        assertFalse(hostPort.hasPort());

        hostPort = HostPort.parse("[::1]:80");
        assertEquals("::1", hostPort.host());
        assertEquals(80, hostPort.ensurePort());

        assertThrows(IllegalArgumentException.class, () -> HostPort.parse("test.com:"));
        assertThrows(IllegalArgumentException.class, () -> HostPort.parse("test.com:abc"));
        assertThrows(IllegalArgumentException.class, () -> HostPort.parse("test.com:abc:123"));
        assertThrows(IllegalArgumentException.class, () -> HostPort.parse("test.com:789123"));
        assertThrows(IllegalArgumentException.class, () -> HostPort.parse("[test.com:123"));
    }


    @Test
    void hasPort() {
        var hostPort = HostPort.of("local");
        assertFalse(hostPort.hasPort());

        hostPort = HostPort.of("local", 8080);
        assertTrue(hostPort.hasPort());

    }


    @Test
    void ensurePort() {
        var hostPort = HostPort.of("local");
        assertThrows(IllegalStateException.class, hostPort::ensurePort);

        hostPort = HostPort.of("local", 8080);
        assertEquals(8080, hostPort.ensurePort());
    }

    @Test
    void toStringTest() {
        var hostPort = HostPort.of("local");
        assertEquals("local", hostPort.toStringWithDefault(80));
        assertEquals("local:80", hostPort.toStringWithPort(80));

        hostPort = HostPort.of("local", 8080);
        assertEquals("local:8080", hostPort.toStringWithDefault(80));
        assertEquals("local:8080", hostPort.toStringWithPort(80));

        hostPort = HostPort.of("local", 80);
        assertEquals("local", hostPort.toStringWithDefault(80));
        assertEquals("local:80", hostPort.toStringWithPort(80));

        hostPort = HostPort.of("::1");
        assertEquals("[::1]", hostPort.toStringWithDefault(80));
        assertEquals("[::1]", hostPort.toString());


        hostPort = HostPort.of("::1", 80);
        assertEquals("[::1]:80", hostPort.toString());
        assertEquals("[::1]", hostPort.toStringWithDefault(80));
        assertEquals("[::1]:80", hostPort.toStringWithDefault(8080));

        assertEquals("[::1]:80", hostPort.toStringWithPort(80));
        assertEquals("[::1]:80", hostPort.toStringWithPort(8080));
    }
}