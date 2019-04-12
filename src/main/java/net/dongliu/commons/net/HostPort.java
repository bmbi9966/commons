package net.dongliu.commons.net;

import java.util.Objects;
import java.util.OptionalInt;

import static java.util.Objects.requireNonNull;

/**
 * Host and Port for a net address
 *
 * TODO: ipv6 address handle
 */
public class HostPort {
    private final String host;
    // -1 mean port not set
    private final int port;

    /**
     * Construct a HostPort with only host
     */
    public HostPort(String host) {
        this.host = requireNonNull(host);
        this.port = -1;
    }

    /**
     * Construct a HostPort with illegal host and port
     */
    public HostPort(String host, int port) {
        this.host = requireNonNull(host);
        this.port = checkPort(port);
    }

    private static int checkPort(int port) {
        if (port < 0 || port > 65535) {
            throw new IllegalArgumentException("illegal port: " + port);
        }
        return port;
    }

    /**
     * Parse net address, return HostPort.
     *
     * @throws IllegalArgumentException if address is not illegal
     */
    public static HostPort parse(String address) {
        requireNonNull(address);
        var index = address.indexOf(':');
        if (index < 0) {
            return new HostPort(address);
        }
        int port;
        try {
            port = Integer.parseInt(address.substring(index + 1));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("invalid address: " + address);
        }
        try {
            return new HostPort(address.substring(0, index), port);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("invalid address: " + address, e);
        }
    }

    /**
     * Return a new HostPort with new host
     */
    public HostPort withHost(String host) {
        return new HostPort(host, port);
    }

    /**
     * Return a new HostPort with new port
     */
    public HostPort withPort(int port) {
        return new HostPort(host, port);
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
    public int portOrThrow() {
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

    @Override
    public String toString() {
        if (port == -1) {
            return host;
        }
        return host + ":" + port;
    }
}
