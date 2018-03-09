package net.dongliu.commons.io;

/**
 * Utils for deal with Closeables and AutoCloseables
 */
public class Closeables {

    /**
     * Close the closeable, if exception occurred, silently swallow it.
     *
     * @param closeable the instance to be close. can be null
     */
    public static void closeQuietly(AutoCloseable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception ignore) {
            }
        }
    }


}
