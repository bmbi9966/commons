package net.dongliu.commons.regex;

import net.dongliu.commons.collection.Lists;
import org.junit.jupiter.api.Test;

import java.util.regex.Pattern;

import static java.util.stream.Collectors.toList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PatternsTest {

    @Test
    void matched() {
        Pattern pattern = Pattern.compile("([\\d.]+)元");
        String text = "人民币1234.56元";
        assertEquals(Lists.of("1234.56元"), Patterns.matched(pattern, text).collect(toList()));
        assertEquals(Lists.of("1234.56"), Patterns.matched(pattern, text, 1).collect(toList()));

        String text2 = "人民币1234.56";
        assertEquals(0, Patterns.matched(pattern, text2).count());
        assertEquals(0, Patterns.matched(pattern, text2, 1).count());
    }

    @Test
    void getAllMatched() {
        Pattern pattern = Pattern.compile("([\\d.]+)元");
        String text = "人民币1234.56元";
        assertEquals(Lists.of("1234.56元"), Patterns.getAllMatched(pattern, text));
        assertEquals(Lists.of("1234.56"), Patterns.getAllMatched(pattern, text, 1));

        String text2 = "人民币1234.56";
        assertTrue(Patterns.getAllMatched(pattern, text2).isEmpty());
        assertTrue(Patterns.getAllMatched(pattern, text2, 1).isEmpty());
    }
}