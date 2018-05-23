package net.dongliu.commons.collection;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MapsTest {

    @Test
    public void of() {
        Map<String, Integer> map = Maps.of(
                "1", 1,
                "2", 2
        );
        assertEquals(2, map.size());
        assertEquals(1, (int) map.get("1"));
    }

    @Test
    public void nullToEmpty() {
        assertEquals(Maps.of(), Maps.nullToEmpty(null));
        assertEquals(Maps.of("1", 1), Maps.nullToEmpty(Maps.of("1", 1)));
    }

    @Test
    public void ofEntries() {
        Map<String, Integer> map = Maps.ofEntries(
                Pair.of("1", 1),
                Pair.of("2", 2)
        );
        assertEquals(2, map.size());
        assertEquals(1, (int) map.get("1"));
    }

    @Test
    void convert() {
        Map<String, Integer> map = Maps.of("1", 1, "2", 2);
        assertEquals(Maps.of(1, 1, 2, 2), Maps.convert(map, Integer::valueOf, v -> v));
        assertEquals(Maps.of("1", "1", "2", "2"), Maps.convert(map, String::valueOf));
    }

    @Test
    void filter() {
        Map<String, Integer> map = Maps.of("1", 1, "2", 2);
        assertEquals(Maps.of("1", 1), Maps.filter(map, e -> e.getValue() < 2));
    }
}