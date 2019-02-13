package net.dongliu.commons.exception;

/**
 * Thrown when meet a unknown java specification version
 */
public class UnknownSpecificationVersionException extends RuntimeException {
    private static final long serialVersionUID = 288931485626369638L;

    public UnknownSpecificationVersionException(String version) {
        super("unknown java specification version " + version);
    }
}
