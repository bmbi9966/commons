package net.dongliu.commons.collection;

import org.junit.jupiter.api.Test;

import java.util.Map;

import static java.util.Map.entry;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SuppressWarnings("deprecation")
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
        assertEquals(Maps.of("1", 1), Maps.filter(map, (k, v) -> v < 2));
    }

    @Test
    void merge() {
        Map<String, Integer> map1 = Maps.of("1", 1, "2", 2);
        Map<String, Integer> map2 = Maps.of("1", 3, "4", 4);
        assertEquals(Maps.of("1", 3, "2", 2, "4", 4), Maps.merge(map1, map2));
    }

    @Test
    void fromKeys() {
        assertEquals(Maps.of("1", 1, "2", 2, "3", 3), Maps.fromKeys(Lists.of("1", "2", "3"), Integer::valueOf));
    }

    @Test
    void fromValues() {
        assertEquals(Maps.of("1", 1, "2", 2, "3", 3), Maps.fromValues(Lists.of(1, 2, 3), String::valueOf));
    }

    @Test
    void newHashMap() {
    }

    @Test
    void newCaseInsensitiveMap() {
        var map = Maps.newCaseInsensitiveMap(entry("test", 1), entry("TEST", 2));
        assertEquals(Maps.of("test", 2), map);
    }

}