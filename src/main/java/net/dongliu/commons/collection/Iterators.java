package net.dongliu.commons.collection;

import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.Iterator;
import java.util.Optional;
import java.util.stream.Stream;

import static java.util.Objects.requireNonNull;

/**
 * Utils methods for Iterator
 */
public class Iterators {

    /**
     * Wrap iterator as Stream
     *
     * @param it  the iterator
     * @param <T> the element type
     * @return Stream contains remaining data in iterator
     */
    public static <T> Stream<T> stream(Iterator<T> it) {
        requireNonNull(it);
        return Iterables.stream(() -> it);
    }

    /**
     * Return next element data of iterator. If not has next element, return Optional.empty().
     * The element in Iterator cannot be null, or NullPointerException will be thrown.
     *
     * @param it  the iterator
     * @param <T> the element type
     * @return Optional contains the next element of iterator.
     */
    public static <T> Optional<T> next(Iterator<T> it) {
        requireNonNull(it);
        if (it.hasNext()) {
            return Optional.of(it.next());
        }
        return Optional.empty();
    }

    /**
     * Return next element data of iterator. If not has next element, return null.
     *
     * @param it  the iterator
     * @param <T> the element type
     * @return the next element of iterator.
     */
    @Nullable
    public static <T> T nextOrNull(Iterator<T> it) {
        requireNonNull(it);
        if (it.hasNext()) {
            return it.next();
        }
        return null;
    }
}
