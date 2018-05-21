package net.dongliu.commons.exception;

import java.util.concurrent.TimeoutException;

/**
 * Unchecked version of {@link TimeoutException}.
 */
public class UncheckedTimeoutException extends RuntimeException {
    public UncheckedTimeoutException(TimeoutException cause) {
        super(cause);
    }

    public UncheckedTimeoutException(String message, TimeoutException cause) {
        super(message, cause);
    }
}
