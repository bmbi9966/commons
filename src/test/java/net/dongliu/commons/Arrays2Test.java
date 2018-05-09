package net.dongliu.commons;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

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
}