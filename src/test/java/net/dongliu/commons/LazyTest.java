package net.dongliu.commons;

import net.dongliu.commons.reflection.Classes;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class LazyTest {

    @Test
    public void of() {
        Supplier<String> supplier = mock(Classes.cast(Supplier.class));
        String s = new String("1234");
        when(supplier.get()).thenReturn(s);
        Lazy lazy = Lazy.of(supplier);
        assertSame(s, lazy.get());
        verify(supplier).get();
        assertSame(s, lazy.get());
        verify(supplier).get();
    }

    @Test
    public void exception() {
        Lazy lazy = Lazy.of(() -> {
            throw new RuntimeException();
        });
        assertThrows(RuntimeException.class, lazy::get);
    }

    @Test
    public void multiThread() throws InterruptedException {
        Supplier<String> supplier = mock(Classes.cast(Supplier.class));
        Lazy<String> lazy = Lazy.of(supplier);
        when(supplier.get()).thenReturn("");
        Thread[] threads = new Thread[100];
        for (int i = 0; i < 100; i++) {
            threads[i] = new Thread(lazy::get);
        }
        for (Thread thread : threads) {
            thread.start();
        }
        for (Thread thread : threads) {
            thread.join();
        }
        verify(supplier).get();
    }

    @Test
    public void map() {
        Lazy<Integer> v = Lazy.of(() -> 1);
        Lazy<String> v2 = v.map(i -> i + 1).map(String::valueOf);
        assertEquals("2", v2.get());
    }
}