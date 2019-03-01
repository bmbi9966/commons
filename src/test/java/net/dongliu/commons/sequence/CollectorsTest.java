package net.dongliu.commons.sequence;

import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CollectorsTest {

    @Test
    void max() {
        var collector = Collectors.<Integer>max();
        for (var value : List.of(1, 2, 3, 5, 2, 1, 5)) {
            collector.accept(value);
        }
        assertEquals(Optional.of(5), collector.finish());

        collector = Collectors.max();

        assertEquals(Optional.empty(), collector.finish());

        var collector2 = Collectors.max();
        assertThrows(NullPointerException.class, () -> collector2.accept(null));
    }

    @Test
    void min() {
        var collector = Collectors.<Integer>min();
        for (var value : List.of(1, 2, 3, 5, 2, 1)) {
            collector.accept(value);
        }
        assertEquals(Optional.of(1), collector.finish());

        collector = Collectors.min();

        assertEquals(Optional.empty(), collector.finish());

        var collector2 = Collectors.min();
        assertThrows(NullPointerException.class, () -> collector2.accept(null));
    }
}