package net.dongliu.commons.sequence;

import net.dongliu.commons.collection.Pair;
import net.dongliu.commons.collection.PartitionResult;
import org.junit.jupiter.api.Test;

import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

import static java.util.Comparator.naturalOrder;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class SequenceTest {

    @Test
    void of() {
        assertEquals(3L, Sequence.of(1, 2, 3).count());
    }

    @Test
    void generate() {
        assertEquals(Optional.of(1), Sequence.generate(() -> 1).at(10));
        assertEquals(Optional.of(10L), Sequence.generate(idx -> idx).at(10));
        assertEquals(Optional.of(0), Sequence.generate(() -> 0, prev -> prev + 1).at(0));
        assertEquals(Optional.of(2), Sequence.generate(() -> 0, prev -> prev + 1).at(2));
    }

    @Test
    void empty() {
        assertFalse(Sequence.of().hasNext());
    }

    @Test
    void concat() {
        assertEquals(List.of(1, 2, 3), Sequence.of().concat(Sequence.of(1, 2, 3)).toList());
        assertEquals(List.of(1, 2, 3), Sequence.of(1).concat(Sequence.of(2, 3)).toList());
        assertEquals(List.of(1), Sequence.of(1).concat(Sequence.of()).toList());

        assertEquals(List.of(1, 2), Sequence.of(1).concat(List.of(Sequence.of(), Sequence.of(2))).toList());
        assertEquals(List.of(2), Sequence.of().concat(List.of(Sequence.of(), Sequence.of(2))).toList());
        assertEquals(List.of(), Sequence.of().concat(List.of()).toList());
    }

    @Test
    void map() {
        assertEquals(List.of(1, 2, 3), Sequence.of("1", "2", "3").map(Integer::valueOf).toList());
        assertFalse(Sequence.of().map(Object::toString).hasNext());
    }

    @Test
    void filter() {
        assertEquals(List.of(3), Sequence.of(1, 2, 3).filter(i -> i > 2).toList());
        assertEquals(List.of(), Sequence.of(1, 2, 3).filter(i -> i > 3).toList());
        assertEquals(List.of(), Sequence.<Integer>of().filter(i -> i > 3).toList());
    }

    @Test
    void flatMap() {
        assertEquals(List.of(1, 1, 1, 2), Sequence.of(1, 2).flatMap(i -> Sequence.of(1, i)).toList());
        assertEquals(List.of(1, 1), Sequence.of(1).flatMap(i -> Sequence.of(1, i)).toList());
        assertEquals(List.of(), Sequence.of(1).flatMap(i -> Sequence.of()).toList());
        assertEquals(List.of(), Sequence.of().flatMap(i -> Sequence.of(1, 2)).toList());
    }

    @Test
    void distinct() {
        assertEquals(List.of(1, 2), Sequence.of(1, 1, 1, 2).distinct().toList());
        assertEquals(List.of(), Sequence.of().distinct().toList());
    }

    @Test
    void distinctBy() {
        assertEquals(List.of(1, 2), Sequence.of(1, 1, 1, 2).distinctBy(i -> i + 1).toList());
        assertEquals(List.of(1, 2), Sequence.of(1, 1, 1, 2).distinctBy(i -> i % 2).toList());
        assertEquals(List.of(), Sequence.of().distinctBy(i -> i).toList());
    }


    @Test
    void filterNonNull() {
        assertEquals(List.of(1, 2), Sequence.of(1, 2).filterNonNull().toList());
        assertEquals(List.of(1, 2), Sequence.of(1, 2, null).filterNonNull().toList());
        assertEquals(List.of(), Sequence.of((Integer) null).filterNonNull().toList());
        assertEquals(List.of(), Sequence.of().filterNonNull().toList());
    }

    @Test
    void drop() {
        assertEquals(List.of(2), Sequence.of(1, 2).drop(1).toList());
        assertEquals(List.of(1, 2), Sequence.of(1, 2).drop(0).toList());
        assertEquals(List.of(), Sequence.of(1, 2).drop(2).toList());
        assertEquals(List.of(), Sequence.of(1, 2).drop(3).toList());
        assertEquals(List.of(), Sequence.of().drop(2).toList());
    }

    @Test
    void dropWhile() {
        assertEquals(List.of(2, 3), Sequence.of(1, 2, 3).dropWhile(i -> i <= 1).toList());
        assertEquals(List.of(1, 2, 3), Sequence.of(1, 2, 3).dropWhile(i -> i < 1).toList());
        assertEquals(List.of(), Sequence.of(1, 2, 3).dropWhile(i -> i <= 3).toList());
        assertEquals(List.of(), Sequence.<Integer>of().dropWhile(i -> i <= 3).toList());
    }

    @Test
    void take() {
        assertEquals(List.of(1), Sequence.of(1, 2).take(1).toList());
        assertEquals(List.of(), Sequence.of(1, 2).take(0).toList());
        assertEquals(List.of(1, 2), Sequence.of(1, 2).take(2).toList());
        assertEquals(List.of(1, 2), Sequence.of(1, 2).take(3).toList());
        assertEquals(List.of(), Sequence.of().take(2).toList());
    }

    @Test
    void takeWhile() {
        assertEquals(List.of(1), Sequence.of(1, 2, 3).takeWhile(i -> i <= 1).toList());
        assertEquals(List.of(), Sequence.of(1, 2, 3).takeWhile(i -> i < 1).toList());
        assertEquals(List.of(1, 2, 3), Sequence.of(1, 2, 3).takeWhile(i -> i <= 3).toList());
        assertEquals(List.of(), Sequence.<Integer>of().takeWhile(i -> i <= 3).toList());
    }

    @Test
    void at() {
        assertEquals(Optional.of(1), Sequence.of(1, 2, 3).at(0));
        assertEquals(Optional.empty(), Sequence.of(1, 2, 3).at(10));
        assertEquals(Optional.empty(), Sequence.of().at(0));
    }

    @Test
    void buffered() {
        assertEquals(List.of(List.of(1, 2), List.of(3, 4)), Sequence.of(1, 2, 3, 4).buffered(2).toList());
        assertEquals(List.of(List.of(1, 2), List.of(3)), Sequence.of(1, 2, 3).buffered(2).toList());
        assertEquals(List.of(), Sequence.of().buffered(2).toList());
    }

    @SuppressWarnings("unchecked")
    @Test
    void forEach() {
        Consumer<Integer> consumer = mock(Consumer.class);
        Sequence.of(1, 2).forEach(consumer);
        verify(consumer).accept(eq(1));
        verify(consumer).accept(eq(2));
    }

    @SuppressWarnings("unchecked")
    @Test
    void peek() {
        Consumer<Integer> consumer = mock(Consumer.class);
        assertEquals(List.of(1, 2), Sequence.of(1, 2).peek(consumer).toList());
        verify(consumer).accept(eq(1));
        verify(consumer).accept(eq(2));
    }

    @Test
    void reduce() {
        assertEquals(Integer.valueOf(4), Sequence.of(1, 2).reduce(1, (prev, now) -> prev + now));
        assertEquals(Integer.valueOf(1), Sequence.<Integer>of().reduce(1, (prev, now) -> prev + now));
    }

    @Test
    void collect() {
        assertEquals(Integer.valueOf(4), Sequence.of(1, 1, 2).collect(new CollectConsumer<Integer, Integer>() {
            int total = 0;

            @Override
            public void accept(Integer value) {
                total += value;
            }

            @Override
            public Integer finish() {
                return total;
            }
        }));
    }

    @Test
    void collectToCollection() {
        assertEquals(List.of(1, 1, 2), Sequence.of(1, 1, 2).toCollection(ArrayList::new));
        assertEquals(List.of(), Sequence.of().toCollection(ArrayList::new));
        assertEquals(Set.of(1, 2), Sequence.of(1, 1, 2).toCollection(HashSet::new));
    }

    @Test
    void toArrayList() {
    }

    @Test
    void toList() {
    }

    @Test
    void toHashSet() {
    }

    @Test
    void toSet() {
        assertEquals(Set.of(1, 2), Sequence.of(1, 1, 2).toSet());
        assertEquals(Set.of(), Sequence.of().toSet());
    }

    @Test
    void toHashMap() {
    }

    @Test
    void collectToMap() {
        assertEquals(Map.of("1", 1, "2", 2), Sequence.of(1, 1, 2).toMap(HashMap::new, String::valueOf, i -> i));
        assertEquals(Map.of(), Sequence.of().toMap(HashMap::new, String::valueOf, i -> i));
    }

    @Test
    void toMap() {
        assertEquals(Map.of("1", 1, "2", 2), Sequence.of(1, 1, 2).toMap(String::valueOf, i -> i));
        assertEquals(Map.of(), Sequence.of().toMap(String::valueOf, i -> i));
    }

    @Test
    void groupAndReduce() {
        assertEquals(Map.of(0, 6, 1, 4),
                Sequence.of(1, 2, 3, 4).groupAndReduce(i -> i % 2, () -> 0, (prev, now) -> prev + now));
        assertEquals(Map.of(0, 2, 1, 4),
                Sequence.of(1, 2, 3).groupAndReduce(i -> i % 2, () -> 0, (prev, now) -> prev + now));
    }

    @Test
    void groupAndCollect() {
        assertEquals(Map.of(0, 6, 1, 4),
                Sequence.of(1, 2, 3, 4).groupAndCollect(i -> i % 2, () -> new CollectConsumer<Integer, Integer>() {
                    private int total;

                    @Override
                    public void accept(Integer value) {
                        total += value;
                    }

                    @Override
                    public Integer finish() {
                        return total;
                    }
                }));
    }

    @Test
    void groupToCollection() {
        assertEquals(Map.of(0, List.of(2, 4), 1, List.of(1, 3)),
                Sequence.of(1, 2, 3, 4).groupToCollection(i -> i % 2, ArrayList::new));
    }

    @Test
    void groupToList() {
        assertEquals(Map.of(0, List.of(2, 4), 1, List.of(1, 3)),
                Sequence.of(1, 2, 3, 4).groupToList(i -> i % 2));
    }

    @Test
    void partitionAndReduce() {
    }

    @Test
    void partitionAndCollect() {
    }

    @Test
    void partitionToList() {
        assertEquals(new PartitionResult<>(List.of(2, 4), List.of(1, 3)),
                Sequence.of(1, 2, 3, 4).partitionToList(i -> i % 2 == 0));
        assertEquals(new PartitionResult<>(List.of(), List.of(1, 2, 3, 4)),
                Sequence.of(1, 2, 3, 4).partitionToList(i -> i > 5));
        assertEquals(new PartitionResult<>(List.of(1, 2, 3, 4), List.of()),
                Sequence.of(1, 2, 3, 4).partitionToList(i -> i < 5));
    }

    @Test
    void joinTo() {
    }

    @Test
    void joinToString() {
        assertEquals("1,2,3,4", Sequence.of(1, 2, 3, 4).joinToString(","));
        assertEquals("(1,2,3,4)", Sequence.of(1, 2, 3, 4).joinToString(",", "(", ")"));
    }

    @Test
    void count() {
    }

    @Test
    void maxBy() {
        assertEquals(Optional.of(4), Sequence.of(1, 2, 3, 4).maxBy(naturalOrder()));
        assertEquals(Optional.empty(), Sequence.<Integer>of().maxBy(naturalOrder()));
    }

    @Test
    void minBy() {
        assertEquals(Optional.of(1), Sequence.of(1, 2, 3, 4).minBy(naturalOrder()));
        assertEquals(Optional.empty(), Sequence.<Integer>of().minBy(naturalOrder()));
    }

    @Test
    void first() {
        assertEquals(Optional.of(1), Sequence.of(1, 2, 3, 4).first());
        assertEquals(Optional.empty(), Sequence.of().first());
    }

    @Test
    void last() {
        assertEquals(Optional.of(4), Sequence.of(1, 2, 3, 4).last());
        assertEquals(Optional.empty(), Sequence.of().last());
    }

    @Test
    void find() {
        assertEquals(Optional.of(2), Sequence.of(1, 2, 3, 4).find(i -> i > 1));
        assertEquals(Optional.empty(), Sequence.<Integer>of().find(i -> i > 1));
    }

    @Test
    void findLast() {
        assertEquals(Optional.of(4), Sequence.of(1, 2, 3, 4).findLast(i -> i > 1));
        assertEquals(Optional.of(1), Sequence.of(1, 2, 3, 4).findLast(i -> i < 2));
        assertEquals(Optional.empty(), Sequence.<Integer>of().findLast(i -> i > 1));
    }

    @Test
    void anyMatch() {
        assertTrue(Sequence.of(1, 2, 3, 4).anyMatch(i -> i > 1));
        assertFalse(Sequence.of(1, 2, 3, 4).anyMatch(i -> i > 4));
    }

    @Test
    void allMatch() {
        assertTrue(Sequence.of(1, 2, 3, 4).allMatch(i -> i >= 1));
        assertFalse(Sequence.of(1, 2, 3, 4).allMatch(i -> i > 1));
        assertFalse(Sequence.of(1, 2, 3, 4).allMatch(i -> i > 4));
    }

    @Test
    void asIterable() {
    }

    @Test
    void asStream() {
        assertEquals(List.of(1, 2, 3), Sequence.of(1, 2, 3).asStream().collect(Collectors.toList()));

    }

    @Test
    void sortedBy() {
        assertEquals(List.of(1, 2, 3), Sequence.of(2, 3, 1).sortedBy(naturalOrder()).toList());
    }

    @Test
    void remove() {
    }

    @Test
    void zip() {
        assertFalse(Sequence.zip(Sequence.of(), Sequence.of(1)).hasNext());
        assertEquals(List.of(Pair.of(1, "1"), Pair.of(2, "2")),
                Sequence.zip(Sequence.of(1, 2), Sequence.of("1", "2", "3")).toList());
    }

    @Test
    void toCollection() {
    }
}