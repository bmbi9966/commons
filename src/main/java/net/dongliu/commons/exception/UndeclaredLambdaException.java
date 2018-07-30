package net.dongliu.commons.exception;

import java.io.Serializable;

/**
 * Used for wrap un-declared checked exception in lambda expression body.
 */
public class UndeclaredLambdaException extends RuntimeException implements Serializable {
    private static final long serialVersionUID = 4481716055235908216L;

    public UndeclaredLambdaException(Throwable cause) {
        super(cause);
    }

    /**
     * Return the wrapped checked exception thrown in lambda expression body.
     *
     * @return the checked exception
     */
    public Throwable getLambdaException() {
        return getCause();
    }
}
