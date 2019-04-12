package net.dongliu.commons.exception;

/**
 * Wrapper exception for ReflectiveOperationException
 */
public class UncheckedReflectException extends RuntimeException {

    private static final long serialVersionUID = -7538663995300473947L;

    public UncheckedReflectException(ReflectiveOperationException cause) {
        super(cause);
    }
}
