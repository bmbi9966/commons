package net.dongliu.commons.exception;

/**
 * Thrown when has more elements than expected.
 */
public class TooManyElementsException extends RuntimeException {
    private static final long serialVersionUID = 8780653132321370804L;

    public TooManyElementsException() {
    }

    public TooManyElementsException(String message) {
        super(message);
    }
}
