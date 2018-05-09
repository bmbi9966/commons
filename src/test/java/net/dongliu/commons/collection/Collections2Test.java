package net.dongliu.commons.collection;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class Collections2Test {

    @Test
    public void toArray() {
        List<String> list = Lists.of("1", "2", "3");
        String[] array = Collections2.toArray(list, String[]::new);
        assertArrayEquals(new String[]{"1", "2", "3"}, array);
    }
}