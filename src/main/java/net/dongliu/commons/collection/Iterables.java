package net.dongliu.commons.collection;

import net.dongliu.commons.annotation.Nullable;

import java.util.Iterator;
import java.util.Optional;
import java.util.function.Predicate;
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
     * Fetch the first element accepted by predicate in Iterable.
     * The element should not be null, or NullPointerException will be thrown.
     *
     * @param iterable can not be null
     * @return Optional
     */
    public static <T> Optional<T> find(Iterable<T> iterable, Predicate<? super T> predicate) {
        requireNonNull(iterable);
        for (T e : iterable) {
            if (predicate.test(e)) {
                return Optional.of(e);
            }
        }
        return Optional.empty();
    }

    /**
     * Fetch the first element accepted by predicate in Iterable.
     *
     * @param list can not be null
     * @return The first accepted element. If list is empty, return null.
     */
    @Nullable
    public static <T> T findOrNull(Iterable<T> list, Predicate<? super T> predicate) {
        requireNonNull(list);
        for (T e : list) {
            if (predicate.test(e)) {
                return e;
            }
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

    /**
     * Traverse on a collection.
     *
     * @param iterable the collection
     * @param consumer the consumer
     * @param <T>      the data type
     */
    public static <T> void forEach(Iterable<T> iterable, ElementConsumer<? super T> consumer) {
        requireNonNull(iterable);
        requireNonNull(consumer);
        Iterator<T> iterator = iterable.iterator();
        boolean hasNext = iterator.hasNext();
        if (!hasNext) {
            return;
        }
        do {
            T value = iterator.next();
            hasNext = iterator.hasNext();
            consumer.on(value, !hasNext);
        } while (hasNext);
    }

    /**
     * Traverse on a collection.
     *
     * @param iterable the collection
     * @param consumer the consumer
     * @param <T>      the data type
     */
    public static <T> void forEachIndexed(Iterable<T> iterable, IndexedElementConsumer<? super T> consumer) {
        requireNonNull(iterable);
        requireNonNull(consumer);
        Iterator<T> iterator = iterable.iterator();
        int index = 0;
        while (iterator.hasNext()) {
            T value = iterator.next();
            consumer.on(index++, value);
        }
    }
}
