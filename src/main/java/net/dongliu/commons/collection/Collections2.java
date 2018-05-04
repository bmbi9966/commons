package net.dongliu.commons.collection;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.function.IntFunction;

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
     *
     * @return list contains the result.
     */
    public static <S, T> List<T> convertToList(Collection<S> c, Function<S, T> function) {
        requireNonNull(c);
        Object[] values = new Object[c.size()];
        int i = 0;
        for (S s : c) {
            values[i++] = function.apply(s);
        }
        return new ImmutableList<>(values);
    }
}
