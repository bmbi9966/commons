package net.dongliu.commons;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.UncheckedIOException;

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
        Throwables.throwInternal(t);
        // just for making compiler happy
        return new RuntimeException("should not reach here");
    }


    @SuppressWarnings("unchecked")
    private static <T extends Throwable> void throwInternal(Throwable t) throws T {
        throw (T) t;
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
}
