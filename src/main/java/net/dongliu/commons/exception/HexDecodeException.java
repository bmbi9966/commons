package net.dongliu.commons.exception;

import java.io.Serializable;

/**
 * Thrown when decode hex error occurred.
 */
public class HexDecodeException extends RuntimeException implements Serializable {
    private static final long serialVersionUID = 9011453264263445207L;

    public HexDecodeException(String message) {
        super(message);
    }
}
