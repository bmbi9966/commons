package net.dongliu.commons.sequence;

import net.dongliu.commons.collection.Iterators;
import net.dongliu.commons.collection.Maps;
import net.dongliu.commons.collection.Pair;
import net.dongliu.commons.collection.PartitionResult;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.util.*;
import java.util.function.*;
import java.util.stream.Stream;

import static java.util.Collections.*;
import static java.util.Objects.requireNonNull;

/**
 * Sequence, like {@link java.util.stream.Stream}
 */
public interface Sequence<T> extends Iterator<T> {

    @Override
    boolean hasNext();

    @Override
    T next();

    @Deprecated
    @Override
    default void remove() {
        throw new UnsupportedOperationException("remove");
    }

    /**
     * Returns a sequence of values built from the elements of this sequence and the other sequence with the same index.
     * The resulting sequence ends as soon as the shortest input sequence ends.
     */
    static <S, T> Sequence<Pair<S, T>> zip(Sequence<@NonNull S> s1, Sequence<@NonNull T> s2) {
        return new ZippedSequence<>(s1, s2);
    }

    /**
     * Create sequence from iterator
     */
    static <T> Sequence<T> of(Iterator<T> iterator) {
        requireNonNull(iterator);
        if (!iterator.hasNext()) {
            return of();
        }
        return new IteratorSequence<>((iterator));
    }


    /**
     * Create sequence from iterable
     */
    static <T> Sequence<T> of(Iterable<T> iterable) {
        requireNonNull(iterable);
        return of(iterable.iterator());
    }

    /**
     * Create sequence from collection
     */
    static <T> Sequence<T> of(Collection<T> collection) {
        requireNonNull(collection);
        if (collection.isEmpty()) {
            return of();
        }
        if (collection instanceof List && collection instanceof RandomAccess) {
            return new RandomAccessSequence<>((List<T>) collection);
        }
        return of(collection.iterator());
    }

    /**
     * Create a new Sequence from stream. The stream is then terminated.
     */
    static <T> Sequence<T> of(Stream<T> stream) {
        return Sequence.of(stream.iterator());
    }

    /**
     * Return a empty sequence
     *
     * @param <T> the element type
     */
    @SuppressWarnings("unchecked")
    static <T> Sequence<T> of() {
        return (Sequence<T>) EmptySequence.instance;
    }

    /**
     * Create sequence from values
     */
    @SafeVarargs
    static <T> Sequence<T> of(T... values) {
        return new ArraySequence<>(values);
    }

    /**
     * Generate a sequence, from value supplier
     *
     * @param <T> the value type
     * @return a sequence
     */
    static <T> Sequence<T> generate(Supplier<T> supplier) {
        requireNonNull(supplier);
        return new SupplierGeneratedSequence<>(supplier);
    }

    /**
     * Generate a sequence, according to current index
     *
     * @param <T> the value type
     * @return a sequence
     */
    static <T> Sequence<T> generate(LongFunction<T> generator) {
        requireNonNull(generator);
        return new IndexGeneratedSequence<>(generator);
    }

    /**
     * Generate a sequence, from initial value
     *
     * @param initial   the initial value supplier
     * @param generator generate value from previous value
     * @param <T>       the value type
     * @return a sequence
     */
    static <T> Sequence<T> generate(Supplier<T> initial, UnaryOperator<T> generator) {
        requireNonNull(initial);
        requireNonNull(generator);
        return new ProgressiveGeneratedSequence<>(initial, generator);
    }

    /**
     * Return a Sequence contains elements in both Sequence.
     */
    default Sequence<T> concat(Sequence<T> sequence) {
        requireNonNull(sequence);
        if (!sequence.hasNext()) {
            return this;
        }
        if (!this.hasNext()) {
            return sequence;
        }
        return new AggregatedSequence<>(Sequence.of(this, sequence));
    }

    /**
     * Return a Sequence contains elements in all Sequences.
     */
    default Sequence<T> concat(Collection<? extends Sequence<T>> sequences) {
        requireNonNull(sequences);
        if (sequences.isEmpty()) {
            return this;
        }
        if (!this.hasNext()) {
            return new AggregatedSequence<>(sequences.iterator());
        }
        var list = new ArrayList<Sequence<T>>(sequences.size() + 1);
        list.add(this);
        list.addAll(sequences);
        return new AggregatedSequence<>(list.iterator());
    }

    /**
     * map operator
     */
    default <R> Sequence<R> map(Function<? super T, ? extends R> mapper) {
        if (!hasNext()) {
            return of();
        }
        return new TransformSequence<>(this, requireNonNull(mapper));
    }

    /**
     * filter operator
     */
    default Sequence<T> filter(Predicate<? super T> filter) {
        if (!hasNext()) {
            return of();
        }
        return new FilterSequence<>(this, requireNonNull(filter));
    }

    /**
     * flat map operator
     */
    default <R> Sequence<R> flatMap(Function<? super T, ? extends Sequence<R>> mapper) {
        if (!hasNext()) {
            return of();
        }
        var sequences = map(mapper);
        return new AggregatedSequence<>(sequences);
    }

    /**
     * Return a sequence do not contains duplicated elements.
     *
     * @param keyMapper function to get a element key, to judge if elements are duplicated.
     */
    default <E> Sequence<T> distinctBy(Function<? super T, E> keyMapper) {
        requireNonNull(keyMapper);
        if (!hasNext()) {
            return of();
        }
        Set<E> set = new HashSet<>();
        return new FilterSequence<>(this, v -> set.add(keyMapper.apply(v)));
    }

    /**
     * Return a sequence do not contains duplicated elements.
     */
    default Sequence<T> distinct() {
        return distinctBy(Function.identity());
    }


    /**
     * Return a Sequence only contains NonNull values.
     */
    default Sequence<T> filterNonNull() {
        return filter(Objects::nonNull);
    }

    /**
     * Drop first N elements
     *
     * @param size the elements number to drop
     */
    default Sequence<T> drop(long size) {
        Utils.checkCount(size);
        if (size == 0) {
            return this;
        }
        return new SliceSequence<>(this, size, Long.MAX_VALUE);
    }

    /**
     * Drop elements while all meet elements pass the predicate
     */
    default Sequence<T> dropWhile(Predicate<? super T> predicate) {
        requireNonNull(predicate);
        return new DropWhileSequence<>(this, predicate);
    }

    /**
     * Only take first n elements
     */
    default Sequence<T> take(long size) {
        Utils.checkCount(size);
        if (size == 0) {
            return of();
        }
        return new SliceSequence<>(this, 0, size);
    }

    /**
     * Take elements while all meet elements pass the predicate
     */
    default Sequence<T> takeWhile(Predicate<? super T> predicate) {
        requireNonNull(predicate);
        return new TakeWhileSequence<>(this, predicate);
    }

    /**
     * Return the element at index, return empty Optional if this Sequence do not have enough elements.
     * The elements in Seq should not be null.
     *
     * @param seq the element index, start from zero
     */
    default Optional<T> at(long seq) {
        long index = 0;
        while (hasNext()) {
            T value = next();
            if (index++ == seq) {
                return Optional.of(value);
            }
        }
        return Optional.empty();
    }

    /**
     * Buffer this sequence into a sequence of lists each not exceeding the given size.
     *
     * @param size the max size for buffer
     */
    default Sequence<List<T>> buffered(int size) {
        Utils.checkSize(size);
        if (!hasNext()) {
            return of();
        }
        return new BufferedSequence<>(this, size);
    }

    /**
     * Return a sorted Sequence. A comparator is needed for sort.
     * If T is sub type of Comparable and want to use it's compare impl for sorting, {@link Comparator#naturalOrder()} can be passed in.
     *
     * @param comparator the comparator to sort sequence
     */
    default Sequence<T> sortedBy(Comparator<? super T> comparator) {
        requireNonNull(comparator);
        List<T> list = toArrayList();
        list.sort(comparator);
        return of(list);
    }

    /**
     * Consumer the remained element in this Sequence.
     *
     * @param consumer the consumer
     */
    default void forEach(Consumer<? super T> consumer) {
        requireNonNull(consumer);
        while (hasNext()) {
            consumer.accept(next());
        }
    }

    /**
     * Return a Sequence, with has a side effect when a element is take out, it is consume by specified consumer.
     */
    default Sequence<T> peek(Consumer<? super T> consumer) {
        return new Sequence<>() {
            @Override
            public boolean hasNext() {
                return Sequence.this.hasNext();
            }

            @Override
            public T next() {
                T value = Sequence.this.next();
                consumer.accept(value);
                return value;
            }
        };
    }

    /**
     * reduce operator
     *
     * @param initialValue the initial value
     * @param reducer      the reducer
     * @param <R>          the result value type
     * @return a value calculate by reducer
     */
    default <R> R reduce(R initialValue, BiFunction<? super R, ? super T, ? extends R> reducer) {
        requireNonNull(reducer);
        R value = initialValue;
        while (hasNext()) {
            value = reducer.apply(value, next());
        }
        return value;
    }


    /**
     * Collect elements to collection.
     *
     * @param collectConsumer to create a Collection instance
     * @param <R>       the returned Collection type
     */
    default <R> R collect(CollectConsumer<? super T, ? extends R> collectConsumer) {
        requireNonNull(collectConsumer);
        while (hasNext()) {
            collectConsumer.accept(next());
        }
        return collectConsumer.finish();
    }


    /**
     * Collect elements to collection.
     *
     * @param collectionSupplier to create a Collection instance
     * @param <R>      the returned Collection type
     */
    default <R extends Collection<T>> R toCollection(Supplier<R> collectionSupplier) {
        requireNonNull(collectionSupplier);
        var list = collectionSupplier.get();
        while (hasNext()) {
            list.add(next());
        }
        return list;
    }

    /**
     * reduce to array list.
     */
    default ArrayList<T> toArrayList() {
        return toCollection(ArrayList::new);
    }

    /**
     * reduce to immutable List.
     */
    default List<T> toList() {
        if (!hasNext()) {
            return List.of();
        }
        return unmodifiableList(toArrayList());
    }

    /**
     * reduce to hash set
     */
    default HashSet<T> toHashSet() {
        return toCollection(HashSet::new);
    }

    /**
     * reduce to immutable Set.
     */
    default Set<T> toSet() {
        if (!hasNext()) {
            return Set.of();
        }
        return unmodifiableSet(toHashSet());
    }

    /**
     * reduce to hash map
     *
     * @param keyMapper   the mapper to calculate map key
     * @param valueMapper the mapper to calculate map value
     * @param <K>         map key type
     * @param <V>         map value type
     * @return a new map
     */
    default <K, V> HashMap<K, V> toHashMap(Function<? super T, ? extends K> keyMapper,
                                           Function<? super T, ? extends V> valueMapper) {
        return toMap(HashMap::new, keyMapper, valueMapper);
    }

    /**
     * collect elements to map
     *
     * @param mapSupplier to get a map instance
     * @param keyMapper   the mapper to calculate map key
     * @param valueMapper the mapper to calculate map value
     * @param <K>         map key type
     * @param <V>         map value type
     * @return a new map
     */
    default <K, V, R extends Map<K, V>> R toMap(Supplier<R> mapSupplier,
                                                Function<? super T, ? extends K> keyMapper,
                                                Function<? super T, ? extends V> valueMapper) {
        requireNonNull(mapSupplier);
        requireNonNull(keyMapper);
        requireNonNull(valueMapper);

        var map = mapSupplier.get();
        while (hasNext()) {
            T value = next();
            map.put(keyMapper.apply(value), valueMapper.apply(value));
        }
        return map;
    }

    /**
     * collect elements to immutable Map.
     */
    default <K, V> Map<K, V> toMap(Function<? super T, ? extends K> keyMapper,
                                   Function<? super T, ? extends V> valueMapper) {
        requireNonNull(keyMapper);
        requireNonNull(valueMapper);
        if (!hasNext()) {
            return Map.of();
        }
        return unmodifiableMap(toHashMap(keyMapper, valueMapper));
    }

    /**
     * Group the element by key mapper; for per single key, reduce elements with this key to the result value.
     *
     * @param keyMapper get key from element
     * @param initial   initial value supplier for reducing
     * @param reducer   reduce function
     * @param <K>       the group key type
     * @param <R>       the reduce result type
     * @return a immutable map contains grouped result
     * @
     * @
     */
    default <K, R> Map<K, R> groupAndReduce(Function<? super T, ? extends K> keyMapper, Supplier<R> initial,
                                            BiFunction<? super R, ? super T, ? extends R> reducer) {
        requireNonNull(keyMapper);
        requireNonNull(reducer);
        var map = new HashMap<K, R>();
        while (hasNext()) {
            T value = next();
            K key = keyMapper.apply(value);
            if (!map.containsKey(key)) {
                map.put(key, reducer.apply(initial.get(), value));
            } else {
                map.put(key, reducer.apply(map.get(key), value));
            }
        }
        return unmodifiableMap(map);
    }

    /**
     * Group the element by key mapper; for per single key, collect elements with this key to the result value.
     *
     * @param keyMapper get key from element
     * @param collector the collector supplier
     * @param <K>       the group key type
     * @param <R>       the reduce result type
     * @return a immutable map contains grouped result
     */
    default <K, R> Map<K, R> groupAndCollect(Function<? super T, ? extends K> keyMapper,
                                             Supplier<? extends CollectConsumer<? super T, ? extends R>> collector) {
        requireNonNull(keyMapper);
        requireNonNull(collector);
        var map = new HashMap<K, CollectConsumer<? super T, ? extends R>>();
        while (hasNext()) {
            T value = next();
            K key = keyMapper.apply(value);
            map.computeIfAbsent(key, k -> collector.get()).accept(value);
        }
        return Maps.convert(map, k -> k, CollectConsumer::finish);
    }

    /**
     * Group the element by key mapper; for per single key, reduce elements with this key to the result value.
     *
     * @param keyMapper get key from element
     * @param supplier  supplier to make a Collection instance
     * @param <K>       the group key type
     * @param <R>       the reduce result type
     * @return a immutable map contains grouped result
     */
    default <K, R extends Collection<T>> Map<K, R> groupToCollection(Function<? super T, ? extends K> keyMapper,
                                                                     Supplier<R> supplier) {
        requireNonNull(keyMapper);
        requireNonNull(supplier);
        var map = new HashMap<K, R>();
        while (hasNext()) {
            T value = next();
            K key = keyMapper.apply(value);
            map.computeIfAbsent(key, k -> supplier.get()).add(value);
        }
        return unmodifiableMap(map);
    }

    /**
     * Group the element by key mapper; for per single key, a immutable list contains all elements with this key is constructed.
     *
     * @param keyMapper get key from element
     * @param <K>       the group key type
     * @return a immutable map contains grouped result as immutable list
     */
    default <K> Map<K, List<T>> groupToList(Function<? super T, ? extends K> keyMapper) {
        requireNonNull(keyMapper);
        var map = groupToCollection(keyMapper, ArrayList::new);
        return Maps.convert(map, key -> key, Collections::unmodifiableList);
    }

    /**
     * Partition the elements by predicate, and then do reducer for both Seq of elements.
     *
     * @param predicate predicate for partition
     * @param initial   initial value supplier for reducing
     * @param reducer   reduce function
     * @param <R>       the reduce result type
     * @return a map contains grouped result
     */
    default <R> PartitionResult<R> partitionAndReduce(Predicate<? super T> predicate, Supplier<R> initial,
                                                      BiFunction<? super R, ? super T, ? extends R> reducer) {
        requireNonNull(predicate);
        requireNonNull(reducer);
        R matched = initial.get();
        R missed = initial.get();
        while (hasNext()) {
            T value = next();
            if (predicate.test(value)) {
                matched = reducer.apply(matched, value);
            } else {
                missed = reducer.apply(missed, value);
            }
        }
        return new PartitionResult<>(matched, missed);
    }

    /**
     * Partition the elements by predicate, and then collect elements.
     *
     * @param predicate predicate for partition
     * @param supplier  collection supplier
     * @param <R>       the collection type
     * @return a PartitionResult contains grouped result
     */
    default <R extends Collection<T>> PartitionResult<R> partitionAndCollect(Predicate<? super T> predicate,
                                                                             Supplier<R> supplier) {
        requireNonNull(predicate);
        requireNonNull(supplier);
        R matched = supplier.get();
        R missed = supplier.get();
        while (hasNext()) {
            T value = next();
            if (predicate.test(value)) {
                matched.add(value);
            } else {
                missed.add(value);
            }
        }
        return new PartitionResult<>(matched, missed);
    }

    /**
     * Partition the elements by predicate, and then collect elements to immutable list.
     *
     * @param predicate predicate for partition
     * @return a map contains grouped result as immutable list
     */
    default PartitionResult<List<T>> partitionToList(Predicate<? super T> predicate) {
        var result = partitionAndCollect(predicate, ArrayList::new);
        return new PartitionResult<>(unmodifiableList(result.matched()), unmodifiableList(result.missed()));
    }

    /**
     * Join the elements to String out
     *
     * @param appendable the appendable to append str
     * @param sep        the separator string between elements
     * @param prefix     the prefix to prepend before all elements
     * @param suffix     the suffix to append after elements
     */
    default void joinTo(Appendable appendable, CharSequence sep, CharSequence prefix, CharSequence suffix) {
        requireNonNull(appendable);
        requireNonNull(sep);
        requireNonNull(prefix);
        requireNonNull(suffix);
        try {
            appendable.append(prefix);
            if (hasNext()) {
                T value = next();
                while (hasNext()) {
                    appendable.append(String.valueOf(value)).append(sep);
                    value = next();
                }
                appendable.append(String.valueOf(value));
            }
            appendable.append(suffix);
        } catch (IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    /**
     * Join the elements to String
     *
     * @param sep    the separator string between elements
     * @param prefix the prefix to prepend before all elements
     * @param suffix the suffix to append after elements
     */
    default String joinToString(CharSequence sep, CharSequence prefix, CharSequence suffix) {
        requireNonNull(sep);
        requireNonNull(prefix);
        requireNonNull(suffix);

        var sb = new StringBuilder();
        joinTo(sb, sep, prefix, suffix);
        return sb.toString();
    }

    /**
     * Join the elements to String
     *
     * @param sep the separator string between elements
     */
    default String joinToString(CharSequence sep) {
        return joinToString(sep, "", "");
    }

    /**
     * return the count of elements
     */
    default long count() {
        long count = 0;
        while (hasNext()) {
            count++;
            next();
        }
        return count;
    }

    /**
     * Get the max value by comparator. If Sequence has no element, return empty Optional.
     * Elements in this Sequence should not be null.
     * If T is sub type of Comparable and want to use it's compare impl for comparing, {@link Comparator#naturalOrder()} can be passed in.
     *
     * @param comparator the comparator
     */
    default Optional<T> maxBy(Comparator<? super T> comparator) {
        if (!hasNext()) {
            return Optional.empty();
        }
        T max = next();
        while (hasNext()) {
            T value = next();
            if (comparator.compare(max, value) < 0) {
                max = value;
            }
        }
        return Optional.of(max);
    }

    /**
     * Get the min value by comparator. If Sequence has no element, return empty Optional.
     * Elements in this Sequence should not be null.
     * If T is sub type of Comparable and want to use it's compare impl for comparing, {@link Comparator#naturalOrder()} can be passed in.
     *
     * @param comparator the comparator
     */
    default Optional<T> minBy(Comparator<? super T> comparator) {
        if (!hasNext()) {
            return Optional.empty();
        }
        T min = next();
        while (hasNext()) {
            T value = next();
            if (comparator.compare(min, value) > 0) {
                min = value;
            }
        }
        return Optional.of(min);
    }

    /**
     * Return the first element of Sequence. If sequence is empty, return empty Optional.
     * The element in sequence should not be null.
     */
    default Optional<T> first() {
        if (!hasNext()) {
            return Optional.empty();
        }
        return Optional.of(next());
    }

    /**
     * Return the last element of Sequence. If sequence is empty, return empty Optional.
     * The element in sequence should not be null.
     */
    default Optional<T> last() {
        if (!hasNext()) {
            return Optional.empty();
        }

        T value = next();
        while (hasNext()) {
            value = next();
        }
        return Optional.of(value);
    }

    /**
     * Find and return the first element meet the predicate in Sequence. If none is found, return empty Optional.
     * The element in sequence should not be null.
     */
    default Optional<T> find(Predicate<? super T> predicate) {
        requireNonNull(predicate);
        while (hasNext()) {
            T value = next();
            if (predicate.test(value)) {
                return Optional.of(value);
            }
        }
        return Optional.empty();
    }

    /**
     * Find and return the lat element meet the predicate in Sequence. If none is found, return empty Optional.
     * The element in sequence should not be null.
     */
    default Optional<T> findLast(Predicate<? super T> predicate) {
        requireNonNull(predicate);
        T value = null;
        while (hasNext()) {
            T current = next();
            if (predicate.test(current)) {
                value = requireNonNull(current);
            }
        }
        return Optional.ofNullable(value);
    }

    /**
     * If any element in this sequence meet the predicate
     */
    default boolean anyMatch(Predicate<? super T> predicate) {
        requireNonNull(predicate);
        while (hasNext()) {
            if (predicate.test(next())) {
                return true;
            }
        }
        return false;
    }

    /**
     * If all elements in this sequence meet the predicate
     */
    default boolean allMatch(Predicate<? super T> predicate) {
        requireNonNull(predicate);
        while (hasNext()) {
            if (!predicate.test(next())) {
                return false;
            }
        }
        return true;
    }
    /**
     * Return a iterable that wrap this sequence, and can iterate only once.
     */
    default Iterable<T> asIterable() {
        return () -> this;
    }

    /**
     * Return a Stream that wrap this sequence.
     */
    default Stream<T> asStream() {
        return Iterators.stream(this);
    }

}
