package net.dongliu.commons.exception;

/**
 * Used for wrap un-declared checked exception in lambda expression body.
 */
public class UndeclaredLambdaException extends RuntimeException {
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
