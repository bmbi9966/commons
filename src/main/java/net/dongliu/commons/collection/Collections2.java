package net.dongliu.commons.collection;

import java.util.Collection;
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
        T[] array = maker.apply(c.size());
        int i = 0;
        for (T e : c) {
            array[i++] = e;
        }
        return array;
    }
}
