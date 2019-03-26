package net.dongliu.commons.io;

import java.io.OutputStream;

/**
 * Utils for output stream
 */
public class OutputStreams {

    /**
     * Return a OutputStream which discard all written data
     */
    public static OutputStream blackHole() {
        return BlackHoleOutputStream.instance;
    }

}
