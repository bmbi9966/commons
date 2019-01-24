package net.dongliu.commons;

import net.dongliu.commons.reflection.Classes;
import org.junit.jupiter.api.Test;

import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class MemoizedTest {

    @Test
    void runnable() {
        Runnable runnable = mock(Runnable.class);
        Runnable r = Memoized.runnable(runnable);
        r.run();
        verify(runnable).run();
        r.run();
        verify(runnable).run();
    }

    @Test
    void supplier() {
    }

    @Test
    void function() {
        Function<String, Integer> function = mock(Classes.cast(Function.class));
        when(function.apply(eq("1"))).thenReturn(1);
        when(function.apply(eq("2"))).thenReturn(2);
        Function<String, Integer> f = Memoized.function(function);
        assertEquals(1, f.apply("1").intValue());
        assertEquals(2, f.apply("2").intValue());
        verify(function).apply(eq("1"));
        verify(function).apply(eq("2"));
        assertEquals(1, f.apply("1").intValue());
        assertEquals(2, f.apply("2").intValue());
        verify(function).apply(eq("1"));
        verify(function).apply(eq("2"));
    }
}