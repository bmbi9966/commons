package net.dongliu.commons;

import net.dongliu.commons.collection.Lists;
import net.dongliu.commons.collection.Maps;
import org.junit.jupiter.api.Test;

import java.io.StringWriter;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PrintsTest {

    @Test
    void print() {
        StringWriter writer = new StringWriter();
        Prints.out(writer).print(1, 2, 3);
        assertEquals("1 2 3" + System.lineSeparator(), writer.toString());

        writer = new StringWriter();
        Prints.out(writer).end("").print(1, 2, 3);
        assertEquals("1 2 3", writer.toString());

        writer = new StringWriter();
        Prints.out(writer).end("").print(1, null, 3);
        assertEquals("1 null 3", writer.toString());

        writer = new StringWriter();
        Prints.out(writer).sep(", ").end("").print(1, 2, 3);
        assertEquals("1, 2, 3", writer.toString());


        writer = new StringWriter();
        Prints.out(writer).sep(", ").end("").printValues(Lists.of(1, 2, 3));
        assertEquals("1, 2, 3", writer.toString());

        writer = new StringWriter();
        Prints.out(writer).sep(", ").end("").printMap(Maps.of("1", 1, "2", 2));
        assertEquals("1 = 1, 2 = 2", writer.toString());
    }
}