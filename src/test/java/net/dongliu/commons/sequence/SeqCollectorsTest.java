package net.dongliu.commons.sequence;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SeqCollectorsTest {

    @Test
    void max() {
        var collector = SeqCollectors.<Integer>max();
        for (var value : List.of(1, 2, 3, 5, 2, 1, 5)) {
            collector.accept(value);
        }
        assertEquals(Optional.of(5), collector.finish());

        collector = SeqCollectors.max();

        assertEquals(Optional.empty(), collector.finish());

        var collector2 = SeqCollectors.max();
        assertThrows(NullPointerException.class, () -> collector2.accept(null));
    }

    @Test
    void min() {
        var collector = SeqCollectors.<Integer>min();
        for (var value : List.of(1, 2, 3, 5, 2, 1)) {
            collector.accept(value);
        }
        assertEquals(Optional.of(1), collector.finish());

        collector = SeqCollectors.min();

        assertEquals(Optional.empty(), collector.finish());

        var collector2 = SeqCollectors.min();
        assertThrows(NullPointerException.class, () -> collector2.accept(null));
    }
}