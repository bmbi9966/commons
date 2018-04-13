package net.dongliu.commons;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public class LazyTest {

    @Test
    public void of() {
        Lazy lazy = Lazy.of(Object::new);
        assertSame(lazy.get(), lazy.get());
    }

    @Test(expected = RuntimeException.class)
    public void exception() {
        Lazy lazy = Lazy.of(() -> {
            throw new RuntimeException();
        });
        lazy.get();
    }

    @Test
    public void multiThread() throws InterruptedException {
        MockSupplier supplier = new MockSupplier();
        Lazy<String> lazy = Lazy.of(supplier);
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
        assertEquals("supplier0", lazy.get());
        assertEquals(1, supplier.count.get());
    }

    private static class MockSupplier implements Supplier<String> {
        public final AtomicInteger count = new AtomicInteger(0);

        @Override
        public String get() {
            return "supplier" + count.getAndIncrement();
        }
    }

    @Test
    public void map() {
        Lazy<Integer> v = Lazy.of(() -> 1);
        Lazy<String> v2 = v.map(i -> i + 1).map(String::valueOf);
        assertEquals("2", v2.get());
    }
}