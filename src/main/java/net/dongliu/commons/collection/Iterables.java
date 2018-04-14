package net.dongliu.commons.collection;

import org.jetbrains.annotations.Nullable;

import java.util.Iterator;
import java.util.Optional;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import static java.util.Objects.requireNonNull;

/**
 * Utils methods for Iterable
 */
public class Iterables {

    /**
     * Fetch the first element of iterable.
     * The element should not be null, or NullPointerException will be thrown.
     *
     * @param iterable can not be null
     * @return Optional contain the value, or empty Optional if not exists
     */
    public static <T> Optional<T> first(Iterable<T> iterable) {
        requireNonNull(iterable);
        Iterator<T> iterator = iterable.iterator();
        if (iterator.hasNext()) {
            return Optional.of(iterator.next());
        }
        return Optional.empty();
    }

    /**
     * Fetch the first element of iterable.
     * The element should not be null, or NullPointerException will be thrown.
     *
     * @param iterable can not be null
     * @return the first value, or null if not exists
     */
    @Nullable
    public static <T> T firstOrNull(Iterable<T> iterable) {
        requireNonNull(iterable);
        Iterator<T> iterator = iterable.iterator();
        if (iterator.hasNext()) {
            return iterator.next();
        }
        return null;
    }

    /**
     * Creates a new sequential stream fro iterable.
     *
     * @return new stream
     */
    public static <T> Stream<T> stream(Iterable<T> iterable) {
        requireNonNull(iterable);
        return StreamSupport.stream(iterable.spliterator(), false);
    }
}
