package net.dongliu.commons;

import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class Arrays2Test {

    @Test
    public void convert() {
        String[] array = {"1", "2", "4"};
        Integer[] target = Arrays2.convert(array, Integer[]::new, Integer::valueOf);
        assertArrayEquals(new Integer[]{1, 2, 4}, target);
    }

    @Test
    public void concat() {
        String[] array1 = {"1", "2", "4"};
        String[] array2 = {"4", "6", "8"};
        String[] array = Arrays2.concat(String[]::new, array1, array2);
        assertArrayEquals(new String[]{"1", "2", "4", "4", "6", "8"}, array);
    }

    @Test
    void find() {
        assertEquals(Optional.empty(), Arrays2.find(Arrays2.of(), s -> true));
        assertEquals(Optional.empty(), Arrays2.find(Arrays2.of("1", "2"), s -> false));
        assertEquals(Optional.of("1"), Arrays2.find(Arrays2.of("1", "2"), s -> s.length() == 1));
    }

    @Test
    void reverseFind() {
        assertEquals(Optional.empty(), Arrays2.reverseFind(Arrays2.of(), s -> true));
        assertEquals(Optional.empty(), Arrays2.reverseFind(Arrays2.of("1", "2"), s -> false));
        assertEquals(Optional.of("2"), Arrays2.reverseFind(Arrays2.of("1", "2"), s -> s.length() == 1));
    }
}