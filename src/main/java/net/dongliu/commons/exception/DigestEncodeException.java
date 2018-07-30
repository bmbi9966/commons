package net.dongliu.commons.exception;

import java.security.NoSuchAlgorithmException;

/**
 * Thrown when do digest.
 */
public class DigestEncodeException extends RuntimeException {

    private static final long serialVersionUID = 8120870696460743693L;

    public DigestEncodeException(NoSuchAlgorithmException cause) {
        super(cause);
    }
}
