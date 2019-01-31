package net.dongliu.commons.function;

import net.dongliu.commons.reflection.Classes;
import org.junit.jupiter.api.Test;

import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class FunctionsTest {


    @Test
    void memoize() {
        Function<String, Integer> function = mock(Classes.cast(Function.class));
        when(function.apply(eq("1"))).thenReturn(1);
        when(function.apply(eq("2"))).thenReturn(2);
        Function<String, Integer> f = Functions.memoize(function);
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