package net.dongliu.commons.concurrent;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

import static java.lang.System.currentTimeMillis;
import static org.junit.jupiter.api.Assertions.*;

public class FuturesTest {

    @Test
    public void value() {
        CompletableFuture<Integer> future = Futures.value(1);
        assertEquals(Integer.valueOf(1), future.join());
    }

    @Test
    public void failed() {
        CompletableFuture<Integer> future = Futures.failed(new RuntimeException());
        assertThrows(CompletionException.class, future::join);
    }

    @Test
    public void delay() {
        Duration duration = Duration.ofMillis(10);
        long begin = currentTimeMillis();
        CompletableFuture<Integer> future = Futures.delay(Futures.value(10), duration);
        assertEquals(Integer.valueOf(10), future.join());
        assertTrue(currentTimeMillis() - begin >= duration.toMillis());
    }

    @Test
    public void timeout() {
        CompletableFuture<Integer> future = Futures.delay(10, Duration.ofMillis(10));
        future = Futures.timeout(future, Duration.ofMillis(20));
        assertEquals(Integer.valueOf(10), future.join());

        future = Futures.delay(10, Duration.ofMillis(20));
        future = Futures.timeout(future, Duration.ofMillis(10));
        assertThrows(CompletionException.class, future::join);
    }

    @Test
    public void allOf() {
        CompletableFuture<List<Integer>> future = Futures.allOf(
                Futures.delay(10, Duration.ofMillis(10)),
                Futures.value(1)
        );
        assertEquals(List.of(10, 1), future.join());
    }

    @Test
    public void anyOf() {
        CompletableFuture<Integer> future = Futures.anyOf(
                Futures.delay(10, Duration.ofMillis(10)),
                Futures.value(1)
        );
        assertEquals(Integer.valueOf(1), future.join());
    }
}