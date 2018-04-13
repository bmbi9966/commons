package net.dongliu.commons;

import java.util.Objects;

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
        Objects.requireNonNull(t);
        Throwables.throwInternal(t);
        // just for making compiler happy
        return new RuntimeException("should not reach here");
    }


    @SuppressWarnings("unchecked")
    private static <T extends Throwable> void throwInternal(Throwable t) throws T {
        throw (T) t;
    }
}
