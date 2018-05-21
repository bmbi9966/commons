package net.dongliu.commons;

import net.dongliu.commons.exception.UncheckedReflectiveOperationException;
import net.dongliu.commons.exception.UncheckedTimeoutException;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UncheckedIOException;
import java.util.Optional;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;

import static java.util.Objects.requireNonNull;

/**
 * Utils method for throwable
 */
public class Throwables {

    /**
     * Throw the throwable without need to add throws clause to method signature. Usually, it should use like this:
     * <p>
     * {@code throw Throwables.sneakyThrow(e);}
     * </p>
     *
     * @param t the exception to throw, should not be null
     */
    public static RuntimeException sneakyThrow(Throwable t) {
        requireNonNull(t);
        return Throwables.throwInternal(t);
    }

    @SuppressWarnings("unchecked")
    private static <T extends Throwable> RuntimeException throwInternal(Throwable t) throws T {
        throw (T) t;
    }

    /**
     * If is unchecked (Error, or RuntimeException), throw the throwable
     *
     * @param throwable the throwable
     */
    public static void throwIfUnchecked(Throwable throwable) {
        requireNonNull(throwable);
        throwIf(throwable, RuntimeException.class);
        throwIf(throwable, Error.class);
    }

    /**
     * If throwable is type T, throw it.
     *
     * @param throwable the throwable
     * @param cls       the type class
     * @param <T>       the type
     * @throws T exception
     */
    @SuppressWarnings("unchecked")
    public static <T extends Throwable> void throwIf(Throwable throwable, Class<T> cls) throws T {
        requireNonNull(throwable);
        if (cls.isAssignableFrom(throwable.getClass())) {
            throw (T) throwable;
        }
    }

    /**
     * Get root cause of throwable. If throwable do not have a root cause, return its self.
     *
     * @param throwable the throwable, cannot be null
     * @return the root cause.
     */
    @NotNull
    public static Throwable getRootCause(Throwable throwable) {
        requireNonNull(throwable);
        while (true) {
            Throwable cause = throwable.getCause();
            if (cause == null) {
                return throwable;
            }
            throwable = cause;
        }
    }

    /**
     * Get ancestor cause, util the cause is specific type, or throwable has no cause.
     *
     * @param throwable the throwable, cannot be null
     * @return the cause with specific type, or empty if not such cause.
     */
    @SuppressWarnings("unchecked")
    public static <T extends Throwable> Optional<T> getCauseOf(Throwable throwable, Class<T> cls) {
        requireNonNull(throwable);
        Throwable cause = throwable;
        while (cause != null) {
            if (cls.isAssignableFrom(cause.getClass())) {
                return Optional.of((T) cause);
            }
            cause = cause.getCause();
        }
        return Optional.empty();
    }

    /**
     * Get stack trace as string.
     *
     * @param t the throwable, cannot be null
     * @return the string represent stacktrace of throwable
     */
    @NotNull
    public static String getStackTrace(Throwable t) {
        requireNonNull(t);
        try (StringWriter writer = new StringWriter();
             PrintWriter pw = new PrintWriter(writer)) {
            t.printStackTrace(pw);
            pw.flush();
            return writer.toString();
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    /**
     * If e is unchecked error or exception, throw directly. Else, wrap to correspond runtime exception, and then throw.
     * <p>
     * You may want to tell compiler exception is thrown, use:
     * </p>
     * <pre>
     *  throw wrapAndThrown(e);
     * </pre>
     *
     * @param e the exception
     * @return RuntimeException for throw.just for making compiler happier
     */
    /*public*/ static RuntimeException wrapAndThrow(Throwable e) {
        requireNonNull(e);
        throwIfUnchecked(e);
        if (e instanceof IOException) {
            throw new UncheckedIOException((IOException) e);
        }
        if (e instanceof ReflectiveOperationException) {
            throw new UncheckedReflectiveOperationException((ReflectiveOperationException) e);
        }
        if (e instanceof TimeoutException) {
            throw new UncheckedTimeoutException((TimeoutException) e);
        }
        if (e instanceof ExecutionException) {
            throw new CompletionException(e);
        }
        // Common checked exceptions that not in java.base module.
        // SQLException
        // IntrospectionException
        throw new RuntimeException(e);
    }
}
