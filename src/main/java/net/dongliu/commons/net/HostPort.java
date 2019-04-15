package net.dongliu.commons.net;

import net.dongliu.commons.Characters;
import net.dongliu.commons.Strings;

import java.util.Objects;
import java.util.OptionalInt;

import static java.util.Objects.requireNonNull;

/**
 * Host and Port for a net address
 */
public class HostPort {
    private final String host;
    // -1 mean port not set
    private final int port;
    private final HostType type;


    private HostPort(String host, int port, HostType type) {
        this.host = host;
        this.port = port;
        this.type = type;
    }

    /**
     * Construct a HostPort with only host
     */
    public static HostPort of(String host) {
        requireNonNull(host);
        var type = getHostType(host);
        return new HostPort(host, -1, type);
    }

    /**
     * Construct a HostPort with host and port
     */
    public static HostPort of(String host, int port) {
        requireNonNull(host);
        checkPort(port);
        var type = getHostType(host);
        return new HostPort(host, port, type);
    }

    /**
     * Parse net address, return HostPort.
     *
     * @throws IllegalArgumentException if address is not illegal
     */
    public static HostPort parse(String address) {
        requireNonNull(address);
        var index = address.lastIndexOf(':');
        if (index < 0) {
            // plain host(domain and ipv4) without port
            return HostPort.of(address);
        }

        if (address.lastIndexOf(':', index - 1) >= 0) {
            // should be ipv6
            if (address.charAt(0) != '[') {
                //without bracket and port
                return HostPort.of(address);
            }
            if (address.charAt(address.length() - 1) == ']') {
                //with bracket, without port
                return HostPort.of(address.substring(1, address.length() - 1));
            }
            // with bracket, and port
            // fail thought

        }


        // domain/ipv4/ipv6 and port
        int port;
        try {
            port = Integer.parseInt(address.substring(index + 1));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("invalid address: " + address);
        }
        try {
            return HostPort.of(trimIpv6Host(address.substring(0, index)), port);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("invalid address: " + address, e);
        }
    }

    private static String trimIpv6Host(String host) {
        if (host.charAt(0) == '[' && host.charAt(host.length() - 1) == ']') {
            return host.substring(1, host.length() - 1);
        }
        return host;
    }

    private static HostType getHostType(String host) {
        if (isIPv4(host)) {
            return HostType.IPv4;
        }
        if (isDomain(host)) {
            return HostType.domain;
        }
        if (isIPv6(host)) {
            return HostType.IPv6;
        }
        throw new IllegalArgumentException("illegal host: " + host);
    }

    private static boolean isIPv6(String host) {
        //TODO: more strict ipv6 checker
        String[] ipv6Items = host.split(":");
        if (ipv6Items.length <= 2) {
            return false;
        }
        for (String item : ipv6Items) {
            if (!item.isEmpty()) {
                int v;
                try {
                    v = Integer.parseInt(item, 16);
                    if (v < 0 || v > 0xffff) {
                        return false;
                    }
                } catch (NumberFormatException e) {
                    if (!isIPv4(item)) {
                        return false;
                    }
                }

            }
        }
        return true;
    }

    private static boolean isIPv4(String host) {
        var items = host.split("\\.");
        if (items.length != 4) {
            return false;
        }
        for (String item : items) {
            int value = Strings.toInt(item, -1);
            if (value < 0 || value > 255) {
                return false;
            }
        }
        return true;
    }

    private static boolean isDomain(String host) {
        // domain
        if (host.length() > 253) {
            return false;
        }
        var items = host.split("\\.");
        for (String item : items) {
            if (!isDomainLabel(item)) {
                return false;
            }
        }
        return true;
    }

    private static boolean isDomainLabel(String label) {
        if (label.isEmpty() || label.length() > 63) {
            return false;
        }
        if (!canBeDomainStart(label.charAt(0)) || !canBeDomainEnd(label.charAt(label.length() - 1))) {
            return false;
        }
        for (int i = 1; i < label.length() - 1; i++) {
            char c = label.charAt(i);
            if (!canBeDomain(c)) {
                return false;
            }
        }
        return true;
    }

    private static boolean canBeDomainStart(char c) {
        return Characters.isAsciiLetterOrDigit(c);
    }

    private static boolean canBeDomainEnd(char c) {
        return Characters.isAsciiLetterOrDigit(c);
    }

    private static boolean canBeDomain(char c) {
        return Characters.isAsciiLetterOrDigit(c) || c == '-';
    }


    /**
     * Return a new HostPort with new port
     */
    public HostPort withPort(int port) {
        return new HostPort(host, port, type);
    }

    /**
     * the type
     */
    public HostType type() {
        return type;
    }

    /**
     * The host part of address
     */
    public String host() {
        return host;
    }

    /**
     * If has port part
     */
    public boolean hasPort() {
        return port != -1;
    }

    /**
     * If has port, return port; else throw IllegalStateException
     *
     * @throws IllegalStateException if port is not set
     */
    public int ensurePort() {
        if (port == -1) {
            throw new IllegalStateException("do not set a port");
        }
        return port;
    }

    /**
     * The port part of address
     */
    public OptionalInt port() {
        return port == -1 ? OptionalInt.empty() : OptionalInt.of(port);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HostPort hostPort = (HostPort) o;
        return port == hostPort.port &&
                host.equals(hostPort.host);
    }

    @Override
    public int hashCode() {
        return Objects.hash(host, port);
    }

    /**
     * Convert to String representation. If HostPort do not has port, or port equals default port,
     * the returned string do not has port part.
     */
    public String toStringWithDefault(int defaultPort) {
        checkPort(defaultPort);
        if (port == -1 || port == defaultPort) {
            return hostToString(host, type);
        }
        return joinHostAndPort(host, port, type);
    }

    /**
     * Convert to String representation with port part. If HostPort do not has port, using default port.
     */
    public String toStringWithPort(int defaultPort) {
        checkPort(defaultPort);
        if (port == -1) {
            return joinHostAndPort(host, defaultPort, type);
        }
        return joinHostAndPort(host, port, type);
    }

    @Override
    public String toString() {
        if (port == -1) {
            return hostToString(host, type);
        }
        return joinHostAndPort(host, port, type);
    }

    private static int checkPort(int port) {
        if (port < 0 || port > 65535) {
            throw new IllegalArgumentException("illegal port: " + port);
        }
        return port;
    }

    private static String joinHostAndPort(String host, int port, HostType type) {
        if (type == HostType.IPv6) {
            return "[" + host + "]:" + port;
        }
        return host + ":" + port;
    }

    private static String hostToString(String host, HostType type) {
        if (type == HostType.IPv6) {
            return '[' + host + ']';
        }
        return host;
    }
}
