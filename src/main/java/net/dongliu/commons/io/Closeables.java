package net.dongliu.commons.io;


import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.List;

import static java.util.Objects.requireNonNull;

/**
 * Utils for deal with Closeables and AutoCloseables
 */
public class Closeables {

    /**
     * Close the closeable, if exception occurred, silently swallow it.
     *
     * @param closeable the instance to be close. can be null
     */
    public static void closeQuietly(@Nullable AutoCloseable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (Exception ignore) {
            }
        }
    }


    /**
     * Close the closeable, if exception occurred, silently swallow it.
     *
     * @param closeables the instances to be close.
     */
    public static void closeQuietly(@Nullable AutoCloseable @NonNull ... closeables) {
        for (AutoCloseable closeable : closeables) {
            if (closeable != null) {
                try {
                    closeable.close();
                } catch (Exception ignore) {
                }
            }
        }
    }

    /**
     * Close all resources. If exception is throw when close resource, it is added to Suppressed exception list of passed in Throwable.
     * This behave just as try-with resource management.
     * This method would not rethrow the passed in throwable, callers should rethrow it if needed.
     *
     * @param throwable  the origin exception
     * @param closeables the resources to close
     */
    public static void closeAll(@NonNull Throwable throwable, @Nullable AutoCloseable @NonNull ... closeables) {
        requireNonNull(throwable);
        requireNonNull(closeables);
        for (AutoCloseable closeable : closeables) {
            if (closeable == null) {
                continue;
            }
            try {
                closeable.close();
            } catch (Exception e) {
                throwable.addSuppressed(e);
            }
        }
    }

    /**
     * Close all resources. If exception is throw when close resource, it is added to Suppressed exception list of passed in Throwable.
     * This behave just as try-with resource management.
     * This method would not rethrow the passed in throwable, callers should rethrow it if needed.
     *
     * @param throwable  the origin exception
     * @param closeables the resources to close
     */
    public static void closeAll(@NonNull Throwable throwable, @NonNull List<@Nullable AutoCloseable> closeables) {
        requireNonNull(throwable);
        requireNonNull(closeables);
        for (AutoCloseable closeable : closeables) {
            if (closeable == null) {
                continue;
            }
            try {
                closeable.close();
            } catch (Exception e) {
                throwable.addSuppressed(e);
            }
        }
    }
}
