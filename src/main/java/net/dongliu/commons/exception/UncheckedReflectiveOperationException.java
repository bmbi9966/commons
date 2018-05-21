package net.dongliu.commons.exception;

/**
 * Uncheck version of {@link ReflectiveOperationException}.
 */
public class UncheckedReflectiveOperationException extends RuntimeException {
    public UncheckedReflectiveOperationException(ReflectiveOperationException cause) {
        super(cause);
    }

    public UncheckedReflectiveOperationException(String message, ReflectiveOperationException cause) {
        super(message, cause);
    }
}
