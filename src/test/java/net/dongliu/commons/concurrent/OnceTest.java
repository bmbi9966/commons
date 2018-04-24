package net.dongliu.commons.concurrent;

import org.junit.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class OnceTest {

    @Test
    public void run() {
        Once once = Once.create();
        assertTrue(once.run(() -> {
        }));
        assertFalse(once.run(() -> {
        }));
    }

    @Test
    public void runMultiThread() throws InterruptedException {
        AtomicInteger ai = new AtomicInteger();
        Once once = Once.create();
        Thread[] threads = new Thread[100];
        for (int i = 0; i < 100; i++) {
            threads[i] = new Thread(() -> once.run(ai::getAndIncrement));
        }
        for (int i = 0; i < 100; i++) {
            threads[i].start();
        }
        for (int i = 0; i < 100; i++) {
            threads[i].join();
        }
        assertEquals(1, ai.get());
    }
}