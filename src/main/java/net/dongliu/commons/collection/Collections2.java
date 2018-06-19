package net.dongliu.commons.collection;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.function.IntFunction;
import java.util.function.Predicate;

import static java.util.Objects.requireNonNull;

/**
 * Util method for Collection.
 */
public class Collections2 {

    /**
     * Convert collection to array. This method using method reference, work better than the API in Collection. Call this method as:
     * <pre>
     *     List&lt;String&gt; list = Lists.of("1", "2", "3");
     *     String[] array = Collections2.toArray(list, String[]::new);
     * </pre>
     *
     * @param c     the collection
     * @param maker create the target array
     * @param <T>   element type
     * @return the target array containing elements in collection
     */
    public static <T> T[] toArray(Collection<T> c, IntFunction<T[]> maker) {
        requireNonNull(c);
        return c.toArray(maker.apply(c.size()));
    }

    /**
     * Convert origin collection to new List.
     * There are no guarantees on the type, mutability, serializability, or thread-safety of the List returned.
     *
     * @return list contains the result.
     */
    public static <S, T> List<T> convertToList(Collection<S> c, Function<? super S, ? extends T> function) {
        requireNonNull(c);
        if (c.isEmpty()) {
            return Lists.of();
        }
        List<T> list = new ArrayList<>(c.size());
        int i = 0;
        for (S s : c) {
            list.add(function.apply(s));
        }
        return list;
    }

    /**
     * Divide collection to two list, the first list contains elements accepted by predicate,
     * the other contains other elements.
     * There are no guarantees on the type, mutability, serializability, or thread-safety of the List returned.
     *
     * @param list can not be null
     * @return two list
     */
    public static <T> Pair<List<T>, List<T>> partitionToList(Collection<T> list, Predicate<? super T> predicate) {
        requireNonNull(list);
        List<T> list1 = new ArrayList<>(Math.min(16, list.size()));
        List<T> list2 = new ArrayList<>(Math.min(16, list.size()));
        for (T e : list) {
            if (predicate.test(e)) {
                list1.add(e);
            } else {
                list2.add(e);
            }
        }
        return Pair.of(list1, list2);
    }
}
